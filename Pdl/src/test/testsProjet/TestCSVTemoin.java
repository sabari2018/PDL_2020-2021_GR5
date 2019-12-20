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
    String wantedFile = "TemoinEsperantoAndIdo.csv";

    String urlTwo = "https://en.wikipedia.org/wiki/Comparison_between_Esperanto_and_Ido";
    String convertedFileTwo = "Comparison_of_Hokkien_writing_systems_4.csv";
    String wantedFileTwo = "TemoinHokkienWritingSystems.csv";

    String urlTree = "https://en.wikipedia.org/wiki/Comparison_of_archive_formats";
    String convertedFileTree = "Comparison_of_archive_formats_2.csv";
    String wantedFileTree = "TemoinArchiveFormats.csv";

    String urlFour = "https://en.wikipedia.org/wiki/Comparison_of_debuggers";
    String convertedFileFour = "Comparison_of_debuggers_1.csv";
    String wantedFileFour = "TemoinDebuggers.csv";

    String urlFive = "https://en.wikipedia.org/wiki/Comparison_of_Unicode_encodings";
    String convertedFileFive = "Comparison_of_Unicode_encodings_2.csv";
    String wantedFileFive = "TemoinUnicodeEncodings.csv";

    @Before
    public void setUpCSVTemoin(){
        process = new ProcessWikiUrl();
    }

    /**
     * Case : Comparison of a predefined hand-made CSV of a given table with the generated CSV of the same table
     * Result : Generated CSV correspond to hand-made one
     */
    @Test
    public void testTemoin(){
        boolean result = false;
        result = compareTemoinAndConverted(url, wantedFile, convertedFile);
        Assert.assertTrue("Generated file does not correspond expected file", result);

        result = compareTemoinAndConverted(urlTwo, wantedFileTwo, convertedFileTwo);
        Assert.assertTrue("Generated file does not correspond expected file", result);

        result = compareTemoinAndConverted(urlTree, wantedFileTree, convertedFileTree);
        Assert.assertTrue("Generated file does not correspond expected file", result);

        result = compareTemoinAndConverted(urlFour, wantedFileFour, convertedFileFour);
        Assert.assertTrue("Generated file does not correspond expected file", result);

        result = compareTemoinAndConverted(urlFive, wantedFileFive, convertedFileFive);
        Assert.assertTrue("Generated file does not correspond expected file", result);
    }

    /**
     * Class used to compare a hand-made CSV to the software generated one
     * @param givenUrl url with containing the wanted table
     * @param givenWantedFile hand-made CSV file name
     * @param givenConvertedFile anticipated name of the generated CSV
     * @return true if generated CSV correpond to hand-made one, else false
     */
    private boolean compareTemoinAndConverted(String givenUrl, String givenWantedFile, String givenConvertedFile){
        process = new ProcessWikiUrl();

        String url = givenUrl;

        process.addWikiUrl(url);

        process.parseHTML();
        process.convert();

        try {
            FileReader convertedFileReader = new FileReader(System.getProperty("user.dir") + File.separator + "output" + File.separator + "html" + File.separator + givenConvertedFile);
            FileReader wantedFileReader = new FileReader(System.getProperty("user.dir") + File.separator + "temoins" + File.separator + givenWantedFile);
            BufferedReader convertedBufferReader = new BufferedReader(convertedFileReader);
            StringBuilder converted = new StringBuilder();
            BufferedReader wantedBufferReader = new BufferedReader(wantedFileReader);
            StringBuilder wanted = new StringBuilder();

            try {
                String convertedLine = convertedBufferReader.readLine();
                while (convertedLine != null) {
                    converted.append(convertedLine);
                    convertedLine = convertedBufferReader.readLine();
                }
                convertedBufferReader.close();
                convertedFileReader.close();

                String wantedLine = wantedBufferReader.readLine();
                while (wantedLine != null) {
                    wanted.append(wantedLine);
                    wantedLine = wantedBufferReader.readLine();
                }
                convertedBufferReader.close();
                convertedFileReader.close();

            } catch (IOException exception) {
                System.out.println("Reading error : " + exception.getMessage());
            }

            System.out.println("Wanted : " + wanted.toString());
            System.out.println("Conver : " + converted.toString());

            return wanted.toString().equals(converted.toString());
        }
        catch(Exception e){
            System.out.println("File not found");
        }

        return false;
    }
}
