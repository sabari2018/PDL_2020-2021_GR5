package model;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * A public type which permits to convert a table into a CSV file
 */

public class Converter {

    private File csvConvertFile;

    /**
     * Converter constructor, creates folder output/html and output/wikitext
     */
    public Converter () {
        File dirOutput = new File(System.getProperty("user.dir")+File.separator+"output");
        dirOutput.mkdirs();
        File dirHTML = new File(System.getProperty("user.dir")+File.separator+"output"+File.separator+"html");
        dirHTML.mkdirs();
        File dirWiki = new File(System.getProperty("user.dir")+File.separator+"output"+ File.separator+"wikitext");
        dirWiki.mkdirs();
    }

    /**
     * Converts a Table to a CSV file
     * The name of this file has this form : Page url-Table position in the page
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

        csvConvertFile = new File (System.getProperty("user.dir")+folderName+title.trim()+"_"+nbTable+".csv");

        try {
            FileWriter fileWriter = new FileWriter(csvConvertFile);
            for (Map.Entry entry : content.entrySet()) {
                String [] tableContent = (String []) entry.getValue();

                for (int i = 0; i < tableContent.length; i++) {
                    if (i == tableContent.length-1) {
                        fileWriter.write(tableContent[i].trim());
                    }
                    else {
                        fileWriter.write(tableContent[i].trim() +",");
                    }
                }
                fileWriter.write("\n");
            }
            fileWriter.flush();
            fileWriter.close();
        }
        catch (Exception e) {
            System.out.println("Impossible to write in the CSV file"+ e.getMessage());
        }

        return this.fileIsFilled(csvConvertFile);
    }


    /**
     * @param file file to test
     * @return true if the CSV file is fill
     */
    public boolean fileIsFilled (File file) {
        return file.length() > 0;
    }

    /**
     * @return the CSV file fills with the Table
     */
    public File getCsvConvertFile () {
        return csvConvertFile;
    }

}
