package testsProjet;

import model.Converter;
import model.ProcessWikiUrl;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class BenchTest {

    @Test
    public void testBenchExtractors () {

        //Create output directories
        Converter converter = new Converter();

        String outputDirHtml = "output" + File.separator + "html" + File.separator;
        assertTrue("There are no folder CSV output for html", new File(outputDirHtml).isDirectory());

        String outputDirWikitext = "output" + File.separator + "wikitext" + File.separator;
        assertTrue(new File(outputDirWikitext).isDirectory());
        assertTrue("There are no folder CSV output for wikitext",new File(outputDirHtml).isDirectory() );

       int nbUrl = 0;

        try {
            FileReader fileReader = new FileReader(System.getProperty("user.dir") + File.separator +  "wikiurls.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String url;

            while ((url = bufferedReader.readLine()) != null) {
                String csvFileName = mkCSVFileName(url, 1);
                System.out.println("CSV file name: " + csvFileName);
                nbUrl++;
            }
            bufferedReader.close();
            assertEquals(nbUrl, 40);
        }
        catch (IOException e) {
            System.out.println("Erreur "+e);
        }
    }

    private String mkCSVFileName(String url, int n) {
        return url.trim() + "-" + n + ".csv";
    }
}
