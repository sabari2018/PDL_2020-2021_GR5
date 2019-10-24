package testsProjetOriginaux;

import modelOrigin.Fichier;
import modelOrigin.Url;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Logger;

import static org.junit.Assert.*;

public class TestFichier1920 {
    //======= Ajout de tests : projet 1920 =======
    Logger logger = Logger.getLogger("logger");
    private Fichier fichier;

    @Before
    public void before(){
        fichier = new Fichier();
    }

    @Test
    public void testAddUrl1(){
        logger.info("***** TestAddUrl1 - null url *****");
        boolean fail;
        try{
            fail = false;
            fichier.addUrl(null);
        }catch(NullPointerException npe){
            fail = true;
        }
        assertFalse("null case should be handle", fail);
    }

    @Test
    public void testAddUrl2(){
        logger.info("***** TestAddUrl2 - empty url *****");
        assertFalse("empty case should return false", fichier.addUrl(new Url()));
    }

    @Test
    public void testAddUrl3(){
        logger.info("***** TestAddUrl3 *****");
        assertTrue("an accessible french url should be accepted", fichier.addUrl(new Url("https://fr.wikipedia.org/wiki/Liste_des_pr%C3%A9sidents_des_%C3%89tats-Unis")));
        assertTrue("an accessible english url should be accepted", fichier.addUrl(new Url("https://en.wikipedia.org/wiki/Comparison_of_ALGOL_68_and_C%2B%2B")));
        assertTrue("an accessible french url should be accepted even if there is no table", fichier.addUrl(new Url("https://fr.wikipedia.org/wiki/Chat")));
        assertTrue("an accessible english url should be accepted even if there is no table", fichier.addUrl(new Url("https://en.wikipedia.org/wiki/Cat")));
        assertTrue("an accessible page title in english should be accepted", fichier.addUrl(new Url("Comparison_of_ALGOL_68_and_C++")));
        assertTrue("an accessible page title in french should be accepted", fichier.addUrl(new Url("Chat")));
    }

    @Test
    public void testAddUrl4(){
        logger.info("***** TestAddUrl4 *****");
        assertFalse("an inaccessible title should not be accepted", fichier.addUrl(new Url("&aze")));
    }

    @Test
    public void testRemoveUrl(){
        Url url = new Url("https://fr.wikipedia.org/wiki/Liste_des_pr%C3%A9sidents_des_%C3%89tats-Unis");
        Url url2 = new Url();
        Url url3 = new Url("https:/sfsdfsdfsdf/fr.wikipedia.org/wiki/Liste_des_pr%C3%A9sidents_des_%C3%89tats-Unis");
        fichier.addUrl(url);
        assertTrue(fichier.removeUrl(url));
        assertFalse(fichier.removeUrl(null));
        assertFalse(fichier.removeUrl(url2));
        assertFalse(fichier.removeUrl(url3));
    }

    @Test
    public void testProductUrls(){
       fichier.productUrls();
       assertEquals("Size of Set<Url> should be equals of the number of lines in wikiurls.txt",336, fichier.setUrl.size());
    }

    @Test
    public void testProductUrlsWiki(){
       fichier.productUrlsWikitext();
       assertEquals("Size of Set<Url> should be equals of the number of lines in wikiurls.txt",336, fichier.setUrl.size());
    }
}
