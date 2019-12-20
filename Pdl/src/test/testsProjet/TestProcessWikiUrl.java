package testsProjet;

import model.ProcessWikiUrl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestProcessWikiUrl {

    ProcessWikiUrl processWikiUrlTest;

    @Before
    public void setUp(){
        processWikiUrlTest = new ProcessWikiUrl();
    }

    @Test
    public void testAddWikiUrlFromFileValid(){
        processWikiUrlTest.addWikiUrlFromFile("wikiurlstest", false, "en");

        Assert.assertEquals("The size of the list is not ok", processWikiUrlTest.getListWikiUrl().size(), 2);
    }

    @Test
    public void testAddWikiUrlFromFileInvalid(){
        processWikiUrlTest.addWikiUrlFromFile("wikiurlsinvalidtest", false, "en");

        Assert.assertEquals("The size of the list is not ok", processWikiUrlTest.getListWikiUrl().size(), 0);
    }

    @Test
    public void testAddWikiUrl(){
        processWikiUrlTest.addWikiUrl("https://en.wikipedia.org/wiki/Comparison_of_World_War_I_tanks");

        Assert.assertEquals("The url is not added", processWikiUrlTest.getListWikiUrl().size(), 1);
    }

    @Test
    public void testAddWikiUrlInvalid(){
        processWikiUrlTest.addWikiUrl("https://en.wikipa.orgwikiorisoof_W_WarI_tks");

        Assert.assertEquals("The url is added", processWikiUrlTest.getListWikiUrl().size(), 0);
    }

    @Test
    public void testParseHTML(){
        processWikiUrlTest.addWikiUrlFromFile("wikiurlstest", false, "en");
        processWikiUrlTest.parseHTML();

        Assert.assertNotEquals("The parser doesn't do his job", processWikiUrlTest.getListTable().size(), 0);
    }
}
