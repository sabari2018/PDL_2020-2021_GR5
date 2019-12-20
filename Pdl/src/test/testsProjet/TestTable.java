package testsProjet;

import model.ParserHTML;
import model.ParserWikiText;
import model.ProcessWikiUrl;
import model.Table;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

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
    private static String urlHtml;
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
        urlHtml = "https://en.wikipedia.org/wiki/Comparison_of_Chernobyl_and_other_radioactivity_releases";
        phtml = new ParserHTML();
        phtml.setUrlHtml(urlWikiText);
        tabsHtml = phtml.parseHtml();
        //System.out.println(phtml.parseHtml().size());
        //tabHtml = new Table();
        //for (Table tab : tabsHtml) {
            //tabHtml = tab;
            //System.out.println(tabHtml);
        //}

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

    /**
     * Case : Title of Table get with Wikitext
     * Result : the title is the same : "Comparison_of_Chernobyl_and_other_radioactivity_releases"
     */
    @Test
    public void testGetTitleWikitext(){
        assertEquals("The title is not the same", "Comparison_of_Chernobyl_and_other_radioactivity_releases" ,tabWikitext.getTitle());
    }

    /**
     * Case : Extraction type of Table get with Wikitext
     * Result : the extration type is wikitext
     */
    @Test
    public void testGetExtractionTypeWikiText(){
        assertEquals("The extraction type is note wikitext", "wikitext" ,tabWikitext.getExtractionType());
    }

    /**
     * Case : Num of Table get with Wikitext
     * Result : The num of the table : 1
     */
    @Test
    public void testGetNumTableWikiText(){
        assertEquals("The num of the table is not 1", 1 ,tabWikitext.getNumTable());
    }
}
