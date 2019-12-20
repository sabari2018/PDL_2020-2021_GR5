package testsProjet;

import model.ParserHTML;
import model.ParserWikiText;
import model.ProcessWikiUrl;
import model.Table;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 *
 */

public class TestTable {

    /**
     * Attribute of type string that will contain the url WikiText
     */
    private static String urlWikiText;
    /**
     * Attibute of type ParserWikiText
     */
    private static ParserWikiText pwt;
    /**
     * Attibute of type ArrayList<Table> for wikitext tables
     */
    private static ArrayList<Table> tabsWikitext ;
    /**
     * Attibute of type Table for wikitext table
     */
    private static Table tabWikitext ;

    /**
     * Attribute of type string that will contain the url html
     */
    private static String utlHtml;
    /**
     * Attibute of type ParserHTML
     */
    private static ParserHTML phtml;
    /**
     * Attibute of type ArrayList<Table> for HTML tables
     */
    private static ArrayList<Table> tabsHtml;
    /**
     * Attibute of type Table for HTML table
     */
    private static Table tabHtml;

    /**
     *
     */
    @BeforeClass
    public static void setUp(){
        //en HMTL (page avec 1 tableau)
        utlHtml = "https://en.wikipedia.org/wiki/Comparison_of_Chernobyl_and_other_radioactivity_releases";
        phtml = new ParserHTML();
        phtml.setUrlHtml(urlWikiText);
        tabsHtml = phtml.parseHtml();
        tabHtml = new Table();
        for (Table tab : tabsHtml) {
            tabHtml = tab;
        }

        //en Wikitext (page avec 1 tableau)
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Comparison_of_Chernobyl_and_other_radioactivity_releases&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        tabsWikitext = pwt.parseWikiText();
        tabWikitext = new Table();
        for (Table tab : tabsWikitext) {
            tabWikitext = tab;
        }
    }

    @Test
    public void testGetTitleHTML(){
        assertEquals("The title is not the same", "Comparison of Chernobyl and other radioactivity releases" ,tabHtml.getTitle());
    }
}
