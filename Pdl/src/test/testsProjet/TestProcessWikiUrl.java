package testsProjet;

import model.ProcessWikiUrl;
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

    }

}
