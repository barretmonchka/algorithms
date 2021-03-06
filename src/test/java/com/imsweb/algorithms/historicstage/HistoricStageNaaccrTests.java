package com.imsweb.algorithms.historicstage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import au.com.bytecode.opencsv.CSVWriter;

import com.imsweb.layout.LayoutFactory;
import com.imsweb.layout.record.fixed.naaccr.NaaccrLayout;

/**
 * Read in a NAACCR 14 gz file and output a csv with registry, pat id, seq num, yeardx, stage
 * Created by keelg on 6/29/2015.
 */
public class HistoricStageNaaccrTests {

    public static void main(String[] args) throws IOException {

        NaaccrLayout layout = (NaaccrLayout)LayoutFactory.getLayout(LayoutFactory.LAYOUT_ID_NAACCR_14_INCIDENCE);
        CSVWriter writer = new CSVWriter(new FileWriter(new File("C:\\Users\\keelg\\Desktop\\tmpTest\\ut.csv")));

        LineNumberReader reader = new LineNumberReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(new File("C:\\Users\\keelg\\Desktop\\tmpTest\\ut141204.nov14.txd.gz")))));
        Map<String, String> record = layout.readNextRecord(reader);
        while (record != null) {
            // process record...
            String[] line = new String[5];
            line[0] = record.get(layout.getFieldByNaaccrItemNumber(40).getName()); //Regsitry
            line[1] = record.get(layout.getFieldByNaaccrItemNumber(20).getName()); //Pat ID
            line[2] = record.get(layout.getFieldByNaaccrItemNumber(380).getName()); //Sequence number
            line[3] = record.get(layout.getFieldByNaaccrItemNumber(390).getName()); //Yeardx
            line[4] = HistoricStageUtils.computeHistoricStage(record).getResult();
            writer.writeNext(line);
            record = layout.readNextRecord(reader);
        }
        reader.close();
        writer.close();
    }
}