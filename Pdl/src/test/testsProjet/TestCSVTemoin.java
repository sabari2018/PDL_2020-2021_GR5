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
    String convertedFile = "Comparison between Esperanto and Ido - Wikipedia-0.csv";
    String wantedFile = "TemoinEsperantoAndIdo.csv";

    @Before
    public void setUpCSVTemoin(){
        process = new ProcessWikiUrl();
        String url = "https://en.wikipedia.org/wiki/Comparison_between_Esperanto_and_Ido";

        process.addWikiUrl(url);
    }

    @Test
    public void testComparisonBetweenResultAndTemoin(){
        process.parseHTML();
        process.convert();

        try {
            FileReader convertedFileReader = new FileReader(System.getProperty("user.dir") + File.separator + "output" + File.separator + "html" + File.separator + convertedFile);
            FileReader wantedFileReader = new FileReader(System.getProperty("user.dir") + File.separator +  wantedFile);
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


            Assert.assertEquals("Les files ne sont pas pareil", wanted.toString(), converted.toString());
        }
        catch(Exception e){
            System.out.println("File not found");
        }

    }
}
