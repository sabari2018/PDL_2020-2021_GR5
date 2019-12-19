package testsProjet;

import model.Converter;
import model.ProcessWikiUrl;
import model.Table;
import org.junit.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TestConverter {

    private static HashMap<Table, File> results;
    private static Converter converter;

    /**
     * Recover all extracted Tables and associates their CSV files
     */
    @BeforeClass
    public static void setUp (){
        converter = new Converter();
        ProcessWikiUrl processWikiUrl = new ProcessWikiUrl();
        processWikiUrl.addWikiUrlFromFile("wikiurls", false, "en");
        processWikiUrl.parseHTML();
        processWikiUrl.parseWikiText();
        List<Table> tableWiki = processWikiUrl.getListTable();

        results = new HashMap<>();
        File file;
        for (Table table : tableWiki) {
            converter.convertToCSV(table);
            file = converter.getCsvConvertFile();
            results.put(table,file);
        }
    }

    /**
     * Test if the CSV file is created and filled
     */
    @Test
    public void testFileIsCreated () {
        int nbFileFilled = 0;
        ArrayList<String> messageErrors = new ArrayList<String>();
        String message;

        for (Map.Entry<Table, File> entry : results.entrySet()){
            Table table = entry.getKey();
            File file = entry.getValue();

            if (converter.fileIsFilled(file)) {
                nbFileFilled ++;
            }
            else {
                message = "The CSV file for the  table n "+ table.getNumTable()+" of the page "
                            + table.getTitle() + " for the "+ table.getExtractionType()+" extraction type is not filled";
                messageErrors.add(message);
            }
        }
        Assert.assertTrue("There list of file is empty, there is may be no internet connection",!results.isEmpty());
        displayFileErrors(messageErrors);
        Assert.assertEquals ("All files are not filled : "+nbFileFilled+ " have been filled on "+results.size(),results.size(), nbFileFilled);
    }

    /**
     * Check if there the same row number in the table and the CSV
     */
    @Test
    public void testCheckNbRows () {
        int nbFileCorrect = 0;
        ArrayList<String> messageErrors = new ArrayList<String>();
        String message;

        for (Map.Entry<Table, File> entry : results.entrySet()){
            Table table = entry.getKey();
            File file = entry.getValue();

            if (getNbRowsInTheTable(table) == getNbRowsInTheCSV(file)) {
                nbFileCorrect ++;
            }
            else {
                message = "The number of rows in the table n° "+table.getNumTable()+" of the page "+table.getTitle()+
                            " for the "+table.getExtractionType()+ " extraction type does not match";
                messageErrors.add(message);
            }
        }
        Assert.assertTrue("There list of file is empty, there is may be no internet connection",!results.isEmpty());
        displayFileErrors(messageErrors);
        Assert.assertEquals("All files do not contain the correct number of rows : "+nbFileCorrect+
                                                " files contain the right number of rows on "+results.size(),results.size(), nbFileCorrect);
    }

    /**
     * Check if there the same column number in the table and the CSV
     */
    @Test
    public void testCheckNbColumn () {
        int nbFileCorrect = 0;
        ArrayList<String> messageErrors = new ArrayList<String>();
        String message;

        for (Map.Entry<Table, File> entry : results.entrySet()) {
            Table table = entry.getKey();
            File file = entry.getValue();

            ArrayList<Integer> nbColumnTable = getNbColumnInTheTable(table);
            ArrayList<Integer> nbColumnFile = getNbColumnsInTheCSV(file);

            if (nbColumnTable.containsAll(nbColumnFile)) {
                nbFileCorrect++;
            } else {
                message = "The number of columns in the table n°" + table.getNumTable() + " of the page " + table.getTitle() +
                        " for the " + table.getExtractionType() + " extraction type does not match";
                messageErrors.add(message);
            }
        }
        Assert.assertTrue("There list of file is empty, there is may be no internet connection",!results.isEmpty());
        displayFileErrors(messageErrors);
        Assert.assertEquals("All files do not contain the correct number of columns :" + nbFileCorrect +
                " files contain the right number of columns on " + results.size(), results.size(), nbFileCorrect);
    }

    /**
     * Check if the CSV is valid -> same number of column in each row of the CSV file
     */
    @Test
    public void testCsvValid () {
        int nbFileCorrect = 0;
        String message;
        ArrayList<String> errorsMessage = new ArrayList<String>();

        for (Map.Entry<Table, File> entry : results.entrySet()) {
            Table table = entry.getKey();
            File file = entry.getValue();
            int nbCorrectLine = 0;

            try {
                FileReader f = new FileReader(file);
                BufferedReader buffered = new BufferedReader(f);
                String line = buffered.readLine();
                int nbColumnRef = getNbCommas(line);
                line = buffered.readLine();

                while (line != null) {
                    int nbColumn = getNbCommas(line);
                    if (nbColumn == nbColumnRef) {
                        nbCorrectLine++;
                    }
                    line = buffered.readLine();
                }
                buffered.close();

                if (nbCorrectLine == getNbRowsInTheCSV(file)-1) {
                    nbFileCorrect++;
                }
                else {
                    message = "The CSV file is not valid for the table n°"+table.getNumTable()+" of the page "+table.getTitle()+
                            " for the "+table.getExtractionType()+ " extraction type";
                    errorsMessage.add(message);
                }
            }
            catch (IOException e) {
                System.out.println (e);
            }
        }
        Assert.assertTrue("There list of file is empty, there is may be no internet connection",!results.isEmpty());
        displayFileErrors(errorsMessage);
        Assert.assertEquals("There are: "+nbFileCorrect+ " CSV valid " +
                "on "+results.size(),results.size(), nbFileCorrect);
    }

    /**
     * Display information about table which contains erros
     * @param errorsMessage an arrayList which contains all errors messages
     */
    private void displayFileErrors (ArrayList<String> errorsMessage) {
        for (String error : errorsMessage) {
            System.out.println(error);
        }
    }

    /**
     * Find the number of commas in the line. Commas in the quotation
     * marks are not recorded
     * @param line
     * @return the number of commas in a line
     */
    private int getNbCommas (String line) {
        String test = line.replaceAll("(\"[^\"]*\")","");
        int nbCommas = 0;

        for (int i = 0; i < test.length(); i++) {
            if (test.charAt(i) == ',') {
                nbCommas++;
            }
        }

        return nbCommas;
    }

    /**
     *
     * @param table
     * @return an arrayList which contains the number of columns for each row in the Table
     */
    private ArrayList getNbColumnInTheTable (Table table) {
        ArrayList<Integer> nbColumn = new ArrayList<Integer>();
        HashMap<Integer, String []> content = table.getContent();

        for (Map.Entry entry : content.entrySet()) {
            String [] row = (String[]) entry.getValue();
            nbColumn.add(row.length);
        }
        return nbColumn;
    }

    /**
     *
     * @param table
     * @return the number of rows in a Table
     */
    private int getNbRowsInTheTable (Table table) {
        return table.getContent().size();
    }

    /**
     *
     * @param file
     * @return the number of row in a file
     */
    private static int getNbRowsInTheCSV (File file) {
        int nbRows = 0;
        try {
            FileReader f = new FileReader(file);
            BufferedReader buffered = new BufferedReader(f);
            String line = buffered.readLine();
            while (line != null) {
                nbRows ++;
                line = buffered.readLine();
            }
            buffered.close();
        }
        catch (IOException e) {
            System.out.println (e);
        }
        return nbRows;
    }

    /**
     *
     * @param file
     * @return an ArrayList which contains the number of columns for each rows
     */
    private ArrayList getNbColumnsInTheCSV (File file) {
        ArrayList<Integer> nbColumns = new ArrayList<Integer>();

        try {
            FileReader f = new FileReader(file);
            BufferedReader buffered = new BufferedReader(f);
            String line = buffered.readLine();
            while (line != null) {
                nbColumns.add(getNbCommas(line)+1);
                line = buffered.readLine();
            }
            buffered.close();
        }
        catch (IOException e) {
            System.out.println (e);
        }
        return nbColumns;
    }

   /* @AfterClass
    public static void deleteFile () {
        for (Map.Entry<Table, File> entry : results.entrySet()){
            File file = entry.getValue();
            file.delete();
        }
    }*/
}
