package testsProjet;

import model.Converter;
import model.Table;
import org.junit.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TestConverter {

        private static HashMap<Table, File> resultHTML;
        private static HashMap<Table, File> resultWiki;

        @BeforeClass
        public static void setUp () {
            //Init Page1
            String [] ligne1 = {"Aspect","Esperanto","Interlangua"};
            String [] ligne2 = {"Type","Designed","Naturalistic"};
            String [] ligne3 = {"Gender","masculine","thirdPerson"};

            HashMap<Integer,String[]> content = new HashMap<Integer, String []>();
            content.put(1,ligne1);
            content.put(2, ligne2);
            content.put(3,ligne3);

            /*tableHTML = new Table(content,"Test tableau","html",1);
            tableWikitext = new Table(content,"Test tableau","wikitext",1);
            converter = new Converter();

            String folderName = File.separator+"output"+File.separator+tableHTML.getExtractionType()+File.separator;
            fileHTML = new File(System.getProperty("user.dir") +folderName + tableHTML.getTitle().trim() + "-" +tableHTML.getNumTable()+ ".csv");

            folderName = File.separator+"output"+File.separator+tableWikitext.getExtractionType()+File.separator;
            fileWikiText = new File(System.getProperty("user.dir") +folderName + tableWikitext.getTitle().trim() + "-" +tableWikitext.getNumTable()+ ".csv");
        */
        }


        @Test
        public void testFileIsCreated () {
           /* Assert.assertTrue("Conversion HTML : Le fichier n'a pas été crée",converter.convertToCSV(tableHTML));
            Assert.assertTrue("Conversion Wikitext : Le fichier n'a pas été crée",converter.convertToCSV(tableWikitext));*/

            //Tester si le nom du fichier est correct + ajouter test intégration cf TestCSV.java
        }




        private int getNbColumnInTheTable (Table table) {
            int nbColumn = 0;
            HashMap<Integer, String []> content = table.getContent();

            for (Map.Entry entry : content.entrySet()) {
                String [] row = (String[]) entry.getValue();
                nbColumn = row.length;
                break;
            }
            return nbColumn;
        }

        private int getNbRowsInTheTable (Table table) {
            return table.getContent().size();
        }

        private int getNbRowsInTheCSV (File file) {
            int nbRows = 0;

            try {
                FileReader f = new FileReader(file);
                BufferedReader buffered = new BufferedReader(f);
                String line = buffered.readLine();
                while (line != null) {
                    nbRows ++;
                }
                buffered.close();
            }
            catch (IOException e) {
                System.out.println (e);
            }
            return nbRows;
        }

        private int getNbColumnsInTheCSV (File file) {
            int nbColumns = 0;

            try {
                FileReader f = new FileReader(file);
                BufferedReader buffered = new BufferedReader(f);
                String line = buffered.readLine();
                if (line != null) {
                    nbColumns = line.split(",").length;
                }
                buffered.close();
            }
            catch (IOException e) {
                System.out.println (e);
            }

            return nbColumns;
        }

        @AfterClass
        public static void deleteFile () {
           /* fileHTML.delete();
            fileWikiText.delete();*/
        }
}
