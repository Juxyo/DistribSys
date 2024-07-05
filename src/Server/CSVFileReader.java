package Server;

import java.io.*;
import java.util.ArrayList;

public class CSVFileReader {

    public static ArrayList<String[]> readCSV(String filePath) throws IOException {
        ArrayList<String[]> data = new ArrayList<>();
        BufferedReader br = null;
        String line;
        String csvSplitBy = ";";

        try {
            InputStream is= new FileInputStream(filePath);
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                // Use comma as separator
                String[] row = line.split(csvSplitBy);
                data.add(row);
            }
            is.close();
        } finally {
            if (br != null) {
                br.close();
            }
        }

        return data;
    }
}