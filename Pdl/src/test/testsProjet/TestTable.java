package testsProjet;

import model.ParserHTML;
import model.ParserWikiText;
import model.Table;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

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
        phtml.setUrlHtml(urlHtml);
        tabsHtml = phtml.parseHtml();
        tabHtml = tabsHtml.get(0);


        //en Wikitext (page avec 1 tableau)
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Comparison_of_Chernobyl_and_other_radioactivity_releases&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        tabsWikitext = pwt.parseWikiText();
        tabWikitext = tabsWikitext.get(0);
    }

    /**
     * Case : Title of Table get with Hmtl
     * Result : the title is the same : "Comparison_of_Chernobyl_and_other_radioactivity_releases"
     */
    @Test
    public void testGetTitleHmtl(){
        assertEquals("The title is not the same", "Comparison_of_Chernobyl_and_other_radioactivity_releases" ,tabHtml.getTitle());
    }

    /**
     * Case : Extraction type of Table get with Html
     * Result : the extraction type is html
     */
    @Test
    public void testGetExtractionTypeHtml(){
        assertEquals("The extraction type is not html", "html" ,tabHtml.getExtractionType());
    }

    /**
     * Case : Num of Table get with Html
     * Result : The num of the table : 1
     */
    @Test
    public void testGetNumTableHtml(){
        assertEquals("The numero of the table is not 1", 1 ,tabHtml.getNumTable());
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
     * Result : the extraction type is wikitext
     */
    @Test
    public void testGetExtractionTypeWikiText(){
        assertEquals("The extraction type is not wikitext", "wikitext" ,tabWikitext.getExtractionType());
    }

    /**
     * Case : Num of Table get with Wikitext
     * Result : The num of the table : 1
     */
    @Test
    public void testGetNumTableWikiText(){
        assertEquals("The numero of the table is not 1", 1 ,tabWikitext.getNumTable());
    }

    @Test
    public void testGetContentHtml () {
        HashMap<Integer, String []> content = tabHtml.getContent();

        assertEquals("The number of row is not correct", 5, content.size());
        String [] line1 = content.get(0);
        String [] line2 = content.get(1);
        String [] line3 = content.get(2);
        String [] line4 = content.get(3);
        String [] line5 = content.get(4);

        assertEquals("The first column of line 1 is not correct", " Isotope", line1[0]);
        assertEquals("The second column of line 1 is not correct", " Ratio between the release due to the bomb and the Chernobyl accident ", line1[1]);

        assertEquals("The first column of line 2 is not correct", " 90Sr ", line2[0]);
        assertEquals("The second column of line 2 is not correct", " 1:87 ", line2[1]);

        assertEquals("The first column of line 3 is not correct", " 137Cs ", line3[0]);
        assertEquals("The second column of line 3 is not correct", " 1:890 ", line3[1]);

        assertEquals("The first column of line 4 is not correct", " 131I ", line4[0]);
        assertEquals("The second column of line 4 is not correct", " 1:25 ", line4[1]);

        assertEquals("The first column of line 5 is not correct", " 133Xe ", line5[0]);
        assertEquals("The second column of line 5 is not correct", " 1:31 ", line5[1]);
    }

    @Test
    public void testGetContentWikitext () {
        HashMap<Integer, String []> content = tabWikitext.getContent();

        assertEquals("The number of row is not correct", 5, content.size());
        String [] line1 = content.get(0);
        String [] line2 = content.get(1);
        String [] line3 = content.get(2);
        String [] line4 = content.get(3);
        String [] line5 = content.get(4);

        assertEquals("The first column of line 1 is not correct", " Isotope ", line1[0]);
        assertEquals("The second column of line 1 is not correct", " Ratio between the release due to the bomb and the Chernobyl accident", line1[1]);

        assertEquals("The first column of line 2 is not correct", " 90Sr", line2[0]);
        assertEquals("The second column of line 2 is not correct", " 1:87", line2[1]);

        assertEquals("The first column of line 3 is not correct", " 137Cs", line3[0]);
        assertEquals("The second column of line 3 is not correct", " 1:890", line3[1]);

        assertEquals("The first column of line 4 is not correct", " 131I", line4[0]);
        assertEquals("The second column of line 4 is not correct", " 1:25", line4[1]);

        assertEquals("The first column of line 5 is not correct", " 133Xe", line5[0]);
        assertEquals("The second column of line 5 is not correct", " 1:31 ", line5[1]);
    }
}
