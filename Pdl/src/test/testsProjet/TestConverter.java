package testsProjet;

import model.Converter;
import model.ProcessWikiUrl;
import model.Table;
import org.junit.*;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TestConverter {

        private static HashMap<Table, File> results;
        private Converter converter;

        @BeforeClass
        public static void setUp () {
           //Récupérer toutes les Tables extraites et leur associé leur CSV
            Converter converter = new Converter();
            ProcessWikiUrl processWikiUrl = new ProcessWikiUrl();
            processWikiUrl.parseWikiText();
            List<Table> tableWiki = processWikiUrl.getListTable();
            File file;

            for (Table table : tableWiki) {
                converter.convertToCSV(table);
                file = converter.getCsvConvertFile();
                results.put(table,file);
            }
        }


        @Test
        public void testFileIsCreated () {
            for (Table table : results.keySet()){
                //Assert.assertTrue("Le fichier csv pour la page "+table.getTitle()+"n'a pas été rempli", converter.fileIsFilled());
            }
        }

        @Test
        public void checkNbRows () {

        }

        @Test
        public void checkNbColumn () {

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
