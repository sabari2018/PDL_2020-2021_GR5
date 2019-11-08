package testsProjet;

import model.ProcessWikiUrl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestProcessWikiUrl {

    ProcessWikiUrl processWikiUrlTest;

    @Before
    public void setUp(){
        processWikiUrlTest = new ProcessWikiUrl();
    }

    @Test
    public void testAddWikiUrlFromFileValid(){
        processWikiUrlTest.addWikiUrlFromFile("wikiurlstest", false, "en");

        Assert.assertEquals("La taille n'est pas bonne", processWikiUrlTest.getListWikiUrl().size(), 3);
    }

    @Test
    public void testAddWikiUrlFromFileInvalid(){
        processWikiUrlTest.addWikiUrlFromFile("wikiurlsinvalidtest", false, "en");

        Assert.assertEquals("La taille n'est pas bonne", processWikiUrlTest.getListWikiUrl().size(), 0);
    }

    @Test
    public void testAddWikiUrl(){
        processWikiUrlTest.addWikiUrl("https://en.wikipedia.org/wiki/Comparison_of_World_War_I_tanks");

        Assert.assertEquals("L'url ne s'est pas ajouté", processWikiUrlTest.getListWikiUrl().size(), 1);
    }

    @Test
    public void testAddWikiUrlInvalid(){
        processWikiUrlTest.addWikiUrl("https://en.wikipa.orgwikiorisoof_W_WarI_tks");

        Assert.assertEquals("L'url s'est ajouté alors qu'elle ne devrait pas", processWikiUrlTest.getListWikiUrl().size(), 0);
    }

    @Test
    public void testParseHTML(){
        processWikiUrlTest.addWikiUrlFromFile("wikiurlstest", false, "en");
        processWikiUrlTest.parseHTML();

        Assert.assertNotEquals("Le parseur n'as rien parser", processWikiUrlTest.getListTable().size(), 0);
    }
}
