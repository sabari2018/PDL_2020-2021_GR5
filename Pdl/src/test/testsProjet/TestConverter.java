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
            processWikiUrl.addWikiUrlFromFile("test", false, "en");
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
                    message = "Le fichier csv pour la page "+ table.getTitle()+"n'a pas été rempli et le tableau n°"
                            + table.getNumTable() + "pour l'extraction de type "+ table.getExtractionType();
                    messageErrors.add(message);
                }
            }
            for (String error : messageErrors) {
                System.out.println(error);
            }
            Assert.assertEquals ("Tous les fichiers n'ont pas été remplis, "+nbFileFilled+ " ont été remplis sur "+results.size(),results.size(), nbFileFilled);

        }

       @Test
        public void checkNbRows () {
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
                    message = "Le nombre de ligne du tableau n°"+table.getNumTable()+" de la page "+table.getTitle()+
                            " pour l'extraction de type "+table.getExtractionType()+ " ne correspond pas";
                    messageErrors.add(message);
                }
            }
           for (String error : messageErrors) {
               System.out.println(error);
           }
            Assert.assertEquals("Tout les fichiers ne contienent pas le bon nombre de lignes : "+nbFileCorrect+
                                                " contiennent le bon nombre de lignes sur "+results.size(),results.size(), nbFileCorrect);
        }

        @Test
        public void checkNbColumn () {
            int nbFileCorrect = 0;
            ArrayList<String> messageErrors = new ArrayList<String>();
            String message;

            for (Map.Entry<Table, File> entry : results.entrySet()){
                Table table = entry.getKey();
                File file = entry.getValue();

                if (getNbColumnInTheTable(table).containsAll(getNbColumnsInTheCSV(file))) {
                    nbFileCorrect ++;
                }
                else {
                    message = "Le nombre de colonne du tableau n°"+table.getNumTable()+" de la page "+table.getTitle()+
                            " pour l'extraction de type "+table.getExtractionType()+ " ne correspond pas";
                    messageErrors.add(message);
                }
            }
            for (String error : messageErrors) {
                System.out.println(error);
            }
            Assert.assertEquals("Tout les fichiers ne contienent pas le bon nombre de colonnes: "+nbFileCorrect+
                    " contiennent le bon nombre de colonnes sur "+results.size(),results.size(), nbFileCorrect);
        }

        private ArrayList getNbColumnInTheTable (Table table) {
            ArrayList<Integer> nbColumn = new ArrayList<Integer>();
            HashMap<Integer, String []> content = table.getContent();

            for (Map.Entry entry : content.entrySet()) {
                String [] row = (String[]) entry.getValue();
                nbColumn.add(row.length);
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

        private ArrayList getNbColumnsInTheCSV (File file) {
            ArrayList<Integer> nbColumns = new ArrayList<Integer>();

            try {
                FileReader f = new FileReader(file);
                BufferedReader buffered = new BufferedReader(f);
                String line = buffered.readLine();
                while (line != null) {
                    nbColumns.add(line.split(",").length);
                    line = buffered.readLine();
                }
                buffered.close();
            }
            catch (IOException e) {
                System.out.println (e);
            }

            return nbColumns;
        }
/*
        @AfterClass
        public static void deleteFile () {
            for (Map.Entry<Table, File> entry : results.entrySet()){
                File file = entry.getValue();
                file.delete();
            }
        }*/
}
