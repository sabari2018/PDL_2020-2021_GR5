package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Converter {

    /**
     * Converts a Table to a CSV file
     * @param table Table to convert
     * @return true if the Table is converted false if is not
     */
    public boolean convertToCSV (Table table) {
        HashMap <Integer, String []> content = table.getContent();
        String title = table.getTitle();
        String extractionType = table.getExtractionType();
        String nbTable = Integer.toString(table.getNumTable());
        String folderName;

        if (extractionType.equals("html")) {
            folderName = File.separator+"output"+File.separator+"html"+File.separator;
        }else {
            folderName = File.separator+"output"+File.separator+"wikitext"+File.separator;
        }

        try {
            FileWriter fileWriter = new FileWriter(System.getProperty("user.dir")+folderName+title.trim()+"-"+nbTable+".csv");
            for (Map.Entry entry : content.entrySet()) {
                String [] tableContent = (String []) entry.getValue();

                for (int i = 0; i < tableContent.length; i++) {
                    if (i == tableContent.length-1) {
                        fileWriter.write(tableContent[i]);
                    }
                    else {
                        fileWriter.write(tableContent[i] +",");
                    }
                }
                fileWriter.write("\n");
            }
            fileWriter.flush();
            fileWriter.close();
        }
        catch (IOException e) {
            System.out.println("Impossible to write in the CSV file"+ e.getMessage());
        }

        return this.fileIsCreated(folderName,title,nbTable);
    }

    public boolean fileIsCreated (String folderName, String title, String nbTable) {
        File f = new File(System.getProperty("user.dir") + folderName + title.trim() + "-" +nbTable+ ".csv");
        if(f.exists()){
            return true;
        }
        else {
            return false;
        }
    }
}
