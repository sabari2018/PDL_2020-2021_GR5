package testsProjet;

import model.ProcessWikiUrl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Maud
 *
 * Test different URLs: valid or invalid and without a table or with a table
 */

public class TestProcessWikiUrl {

    ProcessWikiUrl processWikiUrlTest;

    @BeforeClass
    public void setUp(){
        processWikiUrlTest = new ProcessWikiUrl();
    }

    @Test
    public void testAddWikiUrlFromFile(){
        processWikiUrlTest.addWikiUrlFromFile("wikiurlstest", false, "en");

        Assert.assertEquals("La taille n'est pas bonne", processWikiUrlTest.getListWikiUrl().size(), 3);
    }

}
