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

    @Before
    public void setUpCSVTemoin(){
        process = new ProcessWikiUrl();
    }

    /**
     * Case : Comparison of a predefined well made CSV of a given table with the generated CSV of the same table
     * Result : Generated CSV correspond to hand-made one
     */
    @Test
    public void testTemoin(){
        boolean result = false;
        result = compareTemoinAndConverted(url, wantedFile, convertedFile);
        Assert.assertTrue("PAS BON", result);

        result = compareTemoinAndConverted(urlTwo, wantedFileTwo, convertedFileTwo);
        Assert.assertTrue("PAS BON", result);
    }

    /**
     * Class used to compare a hand-made CSV to the software generated one
     * @param givenUrl url with containing the wanted table
     * @param givenWantedFile hand-made CSV file name
     * @param givenConvertedFile anticipated name of the generated CSV
     * @return true if generated CSV correpond to hand-made one, else false
     */
    private boolean compareTemoinAndConverted(String givenUrl, String givenWantedFile, String givenConvertedFile){
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
