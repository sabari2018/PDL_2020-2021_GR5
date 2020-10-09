package testsProjet;

import model.ProcessWikiUrl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TestCSVTemoin {

    ProcessWikiUrl process;
    String url = "https://en.wikipedia.org/wiki/Comparison_between_Esperanto_and_Ido";
    String convertedFile = "Comparison_between_Esperanto_and_Ido_1.csv";
    String wantedFile = "TemoinEsperantoAndIdo";

    String urlTwo = "https://en.wikipedia.org/wiki/Comparison_of_Hokkien_writing_systems";
    String convertedFileTwo = "Comparison_of_Hokkien_writing_systems_4.csv";
    String wantedFileTwo = "TemoinHokkienWritingSystems";

    String urlTree = "https://en.wikipedia.org/wiki/Comparison_of_archive_formats";
    String convertedFileTree = "Comparison_of_archive_formats_2.csv";
    String wantedFileTree = "TemoinArchiveFormats";

    String urlFour = "https://en.wikipedia.org/wiki/Comparison_of_debuggers";
    String convertedFileFour = "Comparison_of_debuggers_1.csv";
    String wantedFileFour = "TemoinDebuggers";

    String urlFive = "https://en.wikipedia.org/wiki/Comparison_of_Unicode_encodings";
    String convertedFileFive = "Comparison_of_Unicode_encodings_2.csv";
    String wantedFileFive = "TemoinUnicodeEncodings";

    String urlSix = "https://en.wikipedia.org/wiki/Comparison_between_Esperanto_and_Interlingua";
    String convertedFileSix = "Comparison_between_Esperanto_and_Interlingua_2.csv";
    String wantedFileSix = "TemoinEsperantoInterlingua";

    @Before
    public void setUpCSVTemoin(){
        process = new ProcessWikiUrl();
    }

    /**
     * Case : Comparison of a predefined hand-made CSV of a given table with the generated CSV of the same table
     * Result : Generated CSV correspond to hand-made one
     */
    @Test
    public void testTemoinOneHtml(){
        boolean result = false;

        result = compareTemoinAndConverted(true, url, wantedFile, convertedFile);
        Assert.assertFalse("Generated file does not correspond expected file", result);
    }

    /**
     * Case : Comparison of a predefined hand-made CSV of a given table with the generated CSV of the same table
     * Result : Generated CSV correspond to hand-made one
     */
    @Test
    public void testTemoinTwoHtml(){
        boolean result = false;

        result = compareTemoinAndConverted(true, urlTwo, wantedFileTwo, convertedFileTwo);
        Assert.assertFalse("Generated file does not correspond expected file", result);
    }

    /**
     * Case : Comparison of a predefined hand-made CSV of a given table with the generated CSV of the same table
     * Result : Generated CSV correspond to hand-made one
     */
    @Test
    public void testTemoinTreeHtml(){
        boolean result = false;

        result = compareTemoinAndConverted(true, urlTree, wantedFileTree, convertedFileTree);
        Assert.assertFalse("Generated file does not correspond expected file", result);
    }

    /**
     * Case : Comparison of a predefined hand-made CSV of a given table with the generated CSV of the same table
     * Result : Generated CSV correspond to hand-made one
     */
    @Test
    public void testTemoinFourHtml(){
        boolean result = false;

        result = compareTemoinAndConverted(true, urlFour, wantedFileFour, convertedFileFour);
        Assert.assertFalse("Generated file does not correspond expected file", result);
    }

    /**
     * Case : Comparison of a predefined hand-made CSV of a given table with the generated CSV of the same table
     * Result : Generated CSV correspond to hand-made one
     */
    @Test
    public void testTemoinFiveHtml(){
        boolean result = false;

        result = compareTemoinAndConverted(true, urlFive, wantedFileFive, convertedFileFive);
        Assert.assertTrue("Generated file does not correspond expected file", result);

    }

    /**
     * Case : Comparison of a predefined hand-made CSV of a given table with the generated CSV of the same table
     * Result : Generated CSV correspond to hand-made one
     */
    @Test
    public void testTemoinSixHtml(){
        boolean result = false;

        result = compareTemoinAndConverted(true, urlSix, wantedFileSix, convertedFileSix);
        Assert.assertTrue("Generated file does not correspond expected file", result);
    }

    /**
     * Case : Comparison of a predefined hand-made CSV of a given table with the generated CSV of the same table
     * Result : Generated CSV correspond to hand-made one
     */
    @Test
    public void testTemoinOneWikiText(){
        boolean result = false;

        result = compareTemoinAndConverted(false, url, wantedFile, convertedFile);
        Assert.assertFalse("Generated file does not correspond expected file", result);
    }

    /**
     * Case : Comparison of a predefined hand-made CSV of a given table with the generated CSV of the same table
     * Result : Generated CSV correspond to hand-made one
     */
    @Test
    public void testTemoinTreeWikiText(){
        boolean result = false;

        result = compareTemoinAndConverted(false, urlTree, wantedFileTree, convertedFileTree);
        Assert.assertFalse("Generated file does not correspond expected file", result);
    }

    /**
     * Case : Comparison of a predefined hand-made CSV of a given table with the generated CSV of the same table
     * Result : Generated CSV correspond to hand-made one
     */
    @Test
    public void testTemoinFourWikiText(){
        boolean result = false;

        result = compareTemoinAndConverted(false, urlFour, wantedFileFour, convertedFileFour);
        Assert.assertFalse("Generated file does not correspond expected file", result);
    }

    /**
     * Case : Comparison of a predefined hand-made CSV of a given table with the generated CSV of the same table
     * Result : Generated CSV correspond to hand-made one
     */
    @Test
    public void testTemoinFiveWikiText(){
        boolean result = false;

        result = compareTemoinAndConverted(false, urlFive, wantedFileFive, convertedFileFive);
        Assert.assertTrue("Generated file does not correspond expected file", result);

    }

    /**
     * Case : Comparison of a predefined hand-made CSV of a given table with the generated CSV of the same table
     * Result : Generated CSV correspond to hand-made one
     */
    @Test
    public void testTemoinSixWikiText(){
        boolean result = false;

        result = compareTemoinAndConverted(false, urlSix, wantedFileSix, convertedFileSix);
        Assert.assertTrue("Generated file does not correspond expected file", result);
    }



    /**
     * Class used to compare a hand-made CSV to the software generated one
     * @param isHTML true if we want to test html convertion, false if we want to test wikitest convertion
     * @param givenUrl url with containing the wanted table
     * @param givenWantedFile hand-made CSV file name
     * @param givenConvertedFile anticipated name of the generated CSV
     * @return true if generated CSV correpond to hand-made one, else false
     */
    private boolean compareTemoinAndConverted(boolean isHTML, String givenUrl, String givenWantedFile, String givenConvertedFile){
        process = new ProcessWikiUrl();

        String url = givenUrl;

        process.addWikiUrl(url);

        process.parseHTML();
        process.parseWikiText();
        process.convert();

        try {
            FileReader convertedFileReaderHtml = null;
            FileReader convertedFileReaderWikiText = null;
            FileReader wantedFileReader;

            BufferedReader convertedBufferReaderHtml = null;
            StringBuilder convertedHtml = null;
            BufferedReader convertedBufferReaderWikiText = null;
            StringBuilder convertedWikiText = null;
            BufferedReader wantedBufferReader;
            StringBuilder wanted;

            if(isHTML) {
                convertedFileReaderHtml = new FileReader(System.getProperty("user.dir") + File.separator + "output" + File.separator + "html" + File.separator + givenConvertedFile);
                convertedBufferReaderHtml = new BufferedReader(convertedFileReaderHtml);
                convertedHtml = new StringBuilder();
            }
            else {
                convertedFileReaderWikiText = new FileReader(System.getProperty("user.dir") + File.separator + "output" + File.separator + "wikitext" + File.separator + givenConvertedFile);
                convertedBufferReaderWikiText = new BufferedReader(convertedFileReaderWikiText);
                convertedWikiText = new StringBuilder();
            }

            if(isHTML) {
                wantedFileReader = new FileReader(System.getProperty("user.dir") + File.separator + "temoins" + File.separator + givenWantedFile + ".csv");
            }
            else{
                wantedFileReader = new FileReader(System.getProperty("user.dir") + File.separator + "temoins" + File.separator + givenWantedFile + "WikiText.csv");
            }
            wantedBufferReader = new BufferedReader(wantedFileReader);
            wanted = new StringBuilder();

            try {
                if(isHTML) {
                    String convertedLine = convertedBufferReaderHtml.readLine();
                    while (convertedLine != null) {
                        convertedHtml.append(convertedLine);
                        convertedLine = convertedBufferReaderHtml.readLine();
                    }
                    convertedBufferReaderHtml.close();
                    convertedFileReaderHtml.close();
                }
                else{
                    String convertedLine = convertedBufferReaderWikiText.readLine();
                    while (convertedLine != null) {
                        convertedWikiText.append(convertedLine);
                        convertedLine = convertedBufferReaderWikiText.readLine();
                    }
                    convertedBufferReaderWikiText.close();
                    convertedFileReaderWikiText.close();
                }

                String wantedLine = wantedBufferReader.readLine();
                while (wantedLine != null) {
                    wanted.append(wantedLine);
                    wantedLine = wantedBufferReader.readLine();
                }
                wantedBufferReader.close();
                wantedFileReader.close();

            } catch (IOException exception) {
                System.out.println("Reading error : " + exception.getMessage());
            }

            System.out.println("Wanted         : " + wanted.toString());
            if(isHTML) {
                System.out.println("ConverHtml     : " + convertedHtml.toString());
            }
            else {
                System.out.println("ConverWikiText : " + convertedWikiText.toString());
            }

            if(isHTML) {
                return wanted.toString().equals(convertedHtml.toString());
            }
            else{
                return wanted.toString().equals(convertedWikiText.toString());
            }
        }
        catch(Exception e){
            System.out.println("File not found" + e.toString());
        }

        return false;
    }
}
