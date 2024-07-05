package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CSVFileReader {

    public static ArrayList<String[]> readCSV(String filePath) throws IOException {
        ArrayList<String[]> data = new ArrayList<>();
        BufferedReader br = null;
        String line;
        String csvSplitBy = ";";

        try {
            InputStream is= CSVFileReader.class.getResourceAsStream(filePath);
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                // Use comma as separator
                String[] row = line.split(csvSplitBy);
                data.add(row);
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }

        return data;
    }
}