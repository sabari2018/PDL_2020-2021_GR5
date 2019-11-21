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

        @BeforeClass
        public static void setUp () {
           //Récupérer toutes les Tables extraites et leur associé leur CSV
            converter = new Converter();
            ProcessWikiUrl processWikiUrl = new ProcessWikiUrl();
            processWikiUrl.addWikiUrlFromFile("wikiurls", false, "en");
            processWikiUrl.parseHTML();
           // processWikiUrl.parseWikiText();
            List<Table> tableWiki = processWikiUrl.getListTable();
            results = new HashMap<Table, File>();
            File file;
            for (Table table : tableWiki) {
                converter.convertToCSV(table);
                file = converter.getCsvConvertFile();
                results.put(table,file);
            }
        }

        @Test
        public void testFileIsCreated () {
            for (Map.Entry<Table, File> entry : results.entrySet()){
                Table table = entry.getKey();
                File file = entry.getValue();
                String message = "Le fichier csv pour la page "+ table.getTitle()+"n'a pas été rempli et le tableau n°"
                        + table.getNumTable() + "pour l'extraction de type "+ table.getExtractionType();
                Assert.assertTrue(message,converter.fileIsFilled(file));
            }
        }

       @Test
        public void checkNbRows () {
            for (Map.Entry<Table, File> entry : results.entrySet()){
                Table table = entry.getKey();
                File file = entry.getValue();
                String message = "Le nombre de ligne du tableau n°"+table.getNumTable()+" de la page "+table.getTitle()+
                        " pour l'extraction de type "+table.getExtractionType()+ " ne correspond pas";
                Assert.assertEquals(message, getNbRowsInTheTable(table), getNbRowsInTheCSV(file));
            }
        }

        @Test
        public void checkNbColumn () {
            for (Map.Entry<Table, File> entry : results.entrySet()){
                Table table = entry.getKey();
                File file = entry.getValue();
                String message = "Le nombre de colonne du tableau n°"+table.getNumTable()+" de la page "+table.getTitle()+
                        " pour l'extraction de type "+table.getExtractionType()+ " ne correspond pas";
                Assert.assertEquals(message, getNbColumnInTheTable(table), getNbColumnsInTheCSV(file));
            }
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
            for (Map.Entry<Table, File> entry : results.entrySet()){
                File file = entry.getValue();
                file.delete();
            }
        }
}
