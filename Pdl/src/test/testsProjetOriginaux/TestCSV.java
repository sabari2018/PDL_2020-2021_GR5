package testsProjetOriginaux;

import modelOrigin.Fichier;
import modelOrigin.ProductionCSV;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Classe de tests de la classe PorductionCSV
 *
 * @author Emile GEORGET
 */
public class TestCSV {

    private static Fichier fHtml;
    private static Fichier fWikitext;
    private static String title = "Test";
    int firstFileIndex = 0;
    int lastFileIndex = 965;
    private ProductionCSV csvVide = new ProductionCSV();
    private ProductionCSV csv = new ProductionCSV("Emile,Georget,Rennes");
    private File[] filesList;
    private FileInputStream fis;
    private InputStreamReader isr;
    private BufferedReader br;
    private Pattern pattern;
    private Matcher matcher;
    private String csvContent;

    /**
     * Deletes the test purpose CSV files
     */
    @AfterClass
    public static void deleteFiles() {
        File file = new File(System.getProperty("user.dir") + File.separator + "output" + File.separator + title.trim() + "-" + 1 + ".csv");
        file.delete();
        file = new File(System.getProperty("user.dir") + File.separator + "output" + File.separator + title.trim() + "-" + 2 + ".csv");
        file.delete();
        file = new File(System.getProperty("user.dir") + File.separator + "output" + File.separator + "wikitext" + File.separator + title.trim() + "-" + 3 + ".csv");
        file.delete();
        file = new File(System.getProperty("user.dir") + File.separator + "output" + File.separator + "wikitext" + File.separator + title.trim() + "-" + 4 + ".csv");
        file.delete();

    }

    /**
     * Set up of the CSV files from HTML
     *
     * @throws IOException
     */
    public void setupHtml() throws IOException {
        fHtml = new Fichier();
        fHtml.productUrls();
        try {
            fHtml.FichierToHTML();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Set up of the CSV files from Wikitext
     *
     * @throws IOException
     */
    public void setupWikiText() throws IOException {
        fWikitext = new Fichier();
        fWikitext.productUrlsWikitext();
        try {
            fWikitext.FichierToWikitext();
        } catch (NullPointerException e) {

        }
    }

    /**
     * Instantiates a BufferedReader to read in a CSV file.
     *
     * @param filesList the list of csv files
     * @param i         the file index of the file to read
     * @throws FileNotFoundException
     */
    public void initReaders(File[] filesList, int i) throws FileNotFoundException {
        fis = new FileInputStream(filesList[i]);
        isr = new InputStreamReader(fis);
        br = new BufferedReader(isr);
    }

    /**
     * @param header the CSV header
     * @return the number of cells in the header
     */
    public int countNbCommas(String header) {
        int nbCommas = 0;
        for (int j = 0; j < header.length(); j++) {
            if (header.charAt(j) == ',') {
                nbCommas++;
            }
        }
        return nbCommas;
    }

    /**
     * Checks if the number of commas on each line of the file is the same than in the header. If it is uniform,
     * the CSV has the same number of cells on each line and will not trigger column errors at import in other programs.
     *
     * @param file the file to check
     * @param i    the number of commas in the header
     * @return true if all the lines of the CSV file have the same number of commas than the header, false otherwise
     * @throws IOException
     */
    public boolean isFileOk(File[] file, int i) throws IOException {
        boolean fileOk = true;
        String line = br.readLine();


        while (line != null) {
            matcher = pattern.matcher(line);
            if (!matcher.find()) {
                fileOk = false;
            }
            line = br.readLine();
        }

        return fileOk;
    }

    /**
     * Checks if a duplicated file is created
     *
     * @param fileName the file name
     * @return true if there is a duplicate, false if there are no duplicates
     */
    public boolean isDuplicate(String fileName) {
        boolean duplicate = false;
        int i = 0;
        File folder = new File(System.getProperty("user.dir") + File.separator + "output" + File.separator + "html" + File.separator);
        filesList = folder.listFiles();
        for (File f : filesList) {
            if (f.getName().equals(fileName)) {
                i++;
            }
        }
        if (i > 1) {
            duplicate = true;
        }
        return duplicate;
    }

    /**
     * Tests the validity of CSVs generated from HTML tables. A valid CSV in this context is a CSV without column errors (see method isFIleOk).
     * This method does not test data integrity.
     * Prints the expected number of CSVs, the actual number of "valid" CSVs and the files causing problems.
     *
     * @throws IOException
     */
    @Test
    public void testValidityCSVFromHTML() throws IOException {
        setupHtml();
        int i = this.firstFileIndex;
        int nbCommas = 0;
        int nbFilesOk = 0;
        ArrayList<String> filesNotOk = new ArrayList<String>();

        File folder = new File(System.getProperty("user.dir") + File.separator + "output" + File.separator + "html" + File.separator);

        filesList = folder.listFiles();

        if (lastFileIndex >= filesList.length) {
            System.out.println("Test interval is too high ! DEFAULT : Test interval set to the whole file set !");
            lastFileIndex = filesList.length - 1;
        }

        while (i < this.lastFileIndex) {
            System.out.println("Fichier n°" + (i + 1) + "/" + (lastFileIndex + 1) + " en cours de test");
            initReaders(filesList, i);
            nbCommas = countNbCommas(br.readLine());
            pattern = Pattern.compile("^(?:[^,]*+,){" + nbCommas + "}[^,]*+$");

            if (isFileOk(filesList, i)) {
                nbFilesOk++;
            } else {
                filesNotOk.add(filesList[i].getName());
            }
            i = i + 1;
        }


        System.out.println("");
        System.out.println("=========================================================");
        System.out.println("**********************TEST RESULTS***********************");
        System.out.println("=========================================================");
        for (String s : filesNotOk) {
            System.out.println(s);
        }
        assertEquals(lastFileIndex + 1, nbFilesOk);
    }

    /**
     * Tests the validity of CSVs generated from Wikitext tables. A valid CSV in this context is a CSV without column errors (see method isFIleOk).
     * This method does not test data integrity.
     * Prints the expected number of CSVs, the actual number of "valid" CSVs and the files causing problems.
     *
     * @throws IOException
     */
    @Test
    public void testValidityCSVFromWikitext() throws IOException {
        setupWikiText();
        int i = this.firstFileIndex;
        int nbCommas = 0;
        int nbFilesOk = 0;
        ArrayList<String> filesNotOk = new ArrayList<String>();

        File folder = new File(System.getProperty("user.dir") + File.separator + "output" + File.separator + "wikitext" + File.separator);

        filesList = folder.listFiles();

        if (lastFileIndex >= filesList.length) {
            System.out.println("Test interval is too high ! DEFAULT : Test interval set to the whole file set !");
            lastFileIndex = filesList.length - 1;
        }

        while (i < this.lastFileIndex) {
            System.out.println("Fichier n°" + (i + 1) + "/" + (lastFileIndex + 1) + " en cours de test");
            initReaders(filesList, i);
            nbCommas = countNbCommas(br.readLine());
            pattern = Pattern.compile("^(?:[^,]*+,){" + nbCommas + "}[^,]*+$");

            if (isFileOk(filesList, i)) {
                nbFilesOk++;
            } else {
                filesNotOk.add(filesList[i].getName());
            }
            i = i + 1;
        }


        System.out.println("");
        System.out.println("=========================================================");
        System.out.println("**********************TEST RESULTS***********************");
        System.out.println("=========================================================");
        for (String s : filesNotOk) {
            System.out.println(s);
        }
        assertEquals(lastFileIndex + 1, nbFilesOk);
    }

    /**
     * Tests if the file creation is done correctly (file created when new ans no duplicates created)
     *
     * @throws IOException
     */
    @Test
    public void testGenerateCSVFromHTML() throws IOException {
        int isCreated;
        //Case 1 "normal" CSV
        isCreated = csv.generateCSVFromHtml(title, 1);
        assertEquals("Erreur, fichier non créé", 0, isCreated);
        //Case 2 empty CSV vide
        isCreated = csvVide.generateCSVFromHtml(title, 2);
        assertEquals("Erreur, fichier non créé", 0, isCreated);
        //Case 3 file already exists
        isCreated = csv.generateCSVFromHtml(title, 2);
        assertFalse("Erreur, fichier créé", isDuplicate("Test-4.csv"));
    }

    /**
     * Tests if the file creation is done correctly (file created when new ans no duplicates created)
     *
     * @throws IOException
     */
    @Test
    public void testGenerateCSVFromWikitext() throws IOException {
        int isCreated;
        //Case 1 "normal" CSV
        isCreated = csv.generateCSVFromWikitext(title, 3);
        assertEquals("Erreur, fichier non créé", 0, isCreated);
        //Case 2 empty CSV vide
        isCreated = csvVide.generateCSVFromWikitext(title, 4);
        assertEquals("Erreur, fichier non créé", 0, isCreated);
        //Case 3 file already exists
        isCreated = csv.generateCSVFromWikitext(title, 4);
        assertFalse("Erreur, fichier créé", isDuplicate("Test-4.csv"));
    }

}
