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

    /**
     * Case : adding valid url from a given file in a processWikiUrl
     * Result : url are added to the processWikiUrl
     */
    @Test
    public void testAddWikiUrlFromFileValid(){
        processWikiUrlTest.addWikiUrlFromFile("wikiurlstest", false, "en");

        Assert.assertEquals("La taille n'est pas bonne", processWikiUrlTest.getListWikiUrl().size(), 2);
    }

    /**
     * Case : adding invalid url from a given file to a processWikiUrl
     * Result : url does not pass to the processWikiUrl
     */
    @Test
    public void testAddWikiUrlFromFileInvalid(){
        processWikiUrlTest.addWikiUrlFromFile("wikiurlsinvalidtest", false, "en");

        Assert.assertEquals("La taille n'est pas bonne", processWikiUrlTest.getListWikiUrl().size(), 0);
    }

    /**
     * Case : adding valid single url to a processWikiUrl
     * Result : url added to the processWikiUrl
     */
    @Test
    public void testAddWikiUrl(){
        processWikiUrlTest.addWikiUrl("https://en.wikipedia.org/wiki/Comparison_of_World_War_I_tanks");

        Assert.assertEquals("L'url ne s'est pas ajouté", processWikiUrlTest.getListWikiUrl().size(), 1);
    }

    /**
     * Case : adding invalid single url to a processWikiUrl
     * Result : url does not pass to the processWikiUrl
     */
    @Test
    public void testAddWikiUrlInvalid(){
        processWikiUrlTest.addWikiUrl("https://en.wikipa.orgwikiorisoof_W_WarI_tks");

        Assert.assertEquals("L'url s'est ajouté alors qu'elle ne devrait pas", processWikiUrlTest.getListWikiUrl().size(), 0);
    }

    /**
     * Case : parsing html url in processWikuUrl
     * Result : url are parssed and tables are added to the processWikiUrl
     */
    @Test
    public void testParseHTML(){
        processWikiUrlTest.addWikiUrlFromFile("wikiurlstest", false, "en");
        processWikiUrlTest.parseHTML();

        Assert.assertNotEquals("Le parseur n'as rien parser", 0, processWikiUrlTest.getListTable().size());
        Assert.assertEquals("Le parseur n'as pas parser le nombre de table voulus", 11, processWikiUrlTest.getListTable().size());
    }

    /**
     * Case : parsing wikitext url in processWikuUrl
     * Result : url are parssed and tables are added to the processWikiUrl
     */
    @Test
    public void testParseWikiText(){
        processWikiUrlTest.addWikiUrlFromFile("wikiurlstest", false, "en");
        processWikiUrlTest.parseWikiText();

        Assert.assertNotEquals("Le parseur n'as rien parser", 0, processWikiUrlTest.getListTable().size());
        Assert.assertEquals("Le parseur n'as pas parser le nombre de table voulus", 11, processWikiUrlTest.getListTable().size());
    }
}
