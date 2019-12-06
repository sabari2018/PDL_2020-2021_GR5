package testsProjet;

import model.ProcessWikiUrl;
import model.Table;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class TestCSVTemoin {

    String csvTemoinFile = "ComparisonOfArchiveFormats-1";
    String row;
    int rowCounter = 0;
    HashMap<Integer, String[]> content = new HashMap<Integer, String[]>();
    Table tableTemoin;

    ProcessWikiUrl processWikiUrl = new ProcessWikiUrl();

    @Before
    public void setUpCSVTemoin(){
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + File.separator + csvTemoinFile + ".csv"));

            try {
                while ((row = csvReader.readLine()) != null) {

                    String[] data = row.split(",");
                    content.put(rowCounter, data);
                    rowCounter++;
                }

                tableTemoin = new Table(content, "Comparison of archive format - Wikipedia", "html", 1);
            }
            catch (IOException exception) {
                System.out.println("Reading error : " + exception.getMessage());
            }
        }
        catch (FileNotFoundException exception) {
            System.out.println("File does not exist");
        }
    }

    @Test
    public void testComparisonBetweenResultAndTemoin(){
        processWikiUrl.addWikiUrlFromFile("wikiurlstesttemoins", false, "en");

        processWikiUrl.parseHTML();
        processWikiUrl.parseWikiText();

        List<Table> listTable = processWikiUrl.getListTable();
        Table resultTable = new Table();

        for(int i = 0; i<listTable.size(); i++){
            if(listTable.get(i).getTitle().equals("Comparison of archive formats - Wikipedia") && listTable.get(i).getNumTable() == 1){
                resultTable = listTable.get(i);
            }
        }

        Assert.assertTrue("PAS BON", resultTable.isEquals(tableTemoin));
    }

}
