package testsProjet;

import model.ParserWikiText;
import model.Table;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

/**
 * Test class of TestParserWikiText
 */

public class TestParserWikiText {

    /**
     * Attribute of type string that will contain the url WikiText
     */
    private String urlWikiText;
    /**
     * Attibute of type ParserWikiText
     */
    private ParserWikiText pwt;

    //Tables tests

    /**
     * Case : No table in the page
     * Result : We should have 0 table
     */
    @Test
    public void testParseWikiTextNoTab() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Kyrkjedalshalsen_Saddle&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();

        assertEquals("We should have 0 table", 0, tabs.size(), tabs.size());
    }

    /**
     * Case : 1 table in the page
     * Result : We should have 1 table
     */
    @Test
    public void testParseWikiText1Tab() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Comparison_of_Chernobyl_and_other_radioactivity_releases&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();

        assertEquals("We should have 1 table", 1, tabs.size());
    }

    /**
     * Case : multiple tables in the page
     * Result : We should have 8 tables
     */
    @Test
    public void testParseWikiText8Tab() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Comparison_between_Esperanto_and_Ido&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();

        assertEquals("We should have 8 tables", 8, tabs.size());
    }

    /**
     * Case : Table without the mention "wikitable"
     * Result : We should have 0 table
     */
    @Test
    public void testParseWikiTextNotWikitable() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=2011_Intersport_Heilbronn_Open_%E2%80%93_Singles&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();

        assertEquals("We should have 0 table", 0, tabs.size());
    }

    //Rows tests

    /**
     * Case : Test rows number in a page with 1 table
     * Result : We should have 5 rows
     */
    @Test
    public void testParseWikiTextNbLign1() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Comparison_of_Chernobyl_and_other_radioactivity_releases&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        int nbRow = 0;

        for (Table tab : tabs
        ) {
            nbRow = tab.getContent().size();
        }
        assertEquals("We should have 5 rows", 5, nbRow);
    }

    /**
     * Case : Test rows number in a page with 1 table with grouped rows
     * Result : We should have 66 rows
     */
    @Test
    public void testParseWikiTextNbLign2() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=1986_1000_km_of_Brands_Hatch&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        int nbRow = 0;

        for (Table tab : tabs
        ) {
            nbRow = tab.getContent().size();
        }
        assertEquals("We should have 66 rows", 66, nbRow);
    }

    /**
     * Case : Test rows number in a page with a table with grouped columns
     * Result : We should have 9 rows
     */
    @Test
    public void testParseWikiTextNbLign3() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Shinichi_Kawano&action=edit&editintro=Template:BLP_editintro";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        int nbRow = 0;

        for (Table tab : tabs
        ) {
            nbRow = tab.getContent().size();
        }
        assertEquals("We should have 9 rows", 9, nbRow);
    }

    /**
     * Case : Test rows number in a page with a table with CSV problems
     * Result : We should have 6 rows
     */
    @Test
    public void testParseWikiTextNbLign4() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Comparison_of_DEX_software&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        int nbRow = 0;

        for (Table tab : tabs
        ) {
            nbRow = tab.getContent().size();
        }
        assertEquals("We should have 6 rows", 6, nbRow);
    }

    /**
     * Case : Test rows number in a page with a table with 2 title ligns
     * Result : We should have 13 rows
     */
    @Test
    public void testParseWikiTextNbLign5() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Comparison_of_PSA_systems&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        int nbRow = 0;

        for (Table tab : tabs
        ) {
            nbRow = tab.getContent().size();
        }
        assertEquals("We should have 13 rows", 13, nbRow);
    }

    /**
     * Case : Test rows number in a page with a table with CSV problems
     * Result : We should have 12 rows
     */
    @Test
    public void testParseWikiTextNbLign6() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Comparison_of_S.M.A.R.T._tools&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        int nbRow = 0;

        for (Table tab : tabs
        ) {
            nbRow = tab.getContent().size();
        }
        assertEquals("We should have 12 rows", 12, nbRow);
    }

    //Cells Tests

    /**
     * Case : Test number of cells in a page with 1 table
     * Result : We should hace 10 cells
     */
    @Test
    public void testParseWikiTextNbCell1() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Comparison_of_Chernobyl_and_other_radioactivity_releases&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        Iterator it;
        int nbcell = 0;

        for (Table tab : tabs) {
            it = tab.getContent().values().iterator();
            while (it.hasNext()) {
                String[] cells = (String[]) it.next();
                nbcell += cells.length;
            }
        }
        assertEquals("We should have 10 cells", 10, nbcell);
    }

    /**
     * Case : Test number of cells in a page with a table with grouped rows
     * Result : We should have 528 cells
     */
    @Test
    public void testParseWikiTextNbCell2() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=1986_1000_km_of_Brands_Hatch&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        Iterator it;
        int nbcell = 0;

        for (Table tab : tabs) {
            it = tab.getContent().values().iterator();
            while (it.hasNext()) {
                String[] cells = (String[]) it.next();
                nbcell += cells.length;
            }
        }
        assertEquals("We should have 528 cells", 528, nbcell);
    }

    /**
     * Case : Test number of cells in a page with a table with grouped columns
     * Result : We should have 99 cells
     */
    @Test
    public void testParseWikiTextNbCell3() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Shinichi_Kawano&action=edit&editintro=Template:BLP_editintro";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        Iterator it;
        int nbcell = 0;

        for (Table tab : tabs) {
            it = tab.getContent().values().iterator();
            while (it.hasNext()) {
                String[] cells = (String[]) it.next();
                nbcell += cells.length;
            }
        }
        assertEquals("We should have 99 cells", 99, nbcell);
    }

    /**
     * Case : Test number of cells in a page with CSV problems
     * Result : We should have 114 cells
     */
    @Test
    public void testParseWikiTextNbCell4() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Comparison_of_DEX_software&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        Iterator it;
        int nbcell = 0;

        for (Table tab : tabs) {
            it = tab.getContent().values().iterator();
            while (it.hasNext()) {
                String[] cells = (String[]) it.next();
                nbcell += cells.length;
            }
        }
        assertEquals("We should have 114 cells", 114, nbcell);
    }

    /**
     * Case : Test number of cells in a page with 2 title ligns
     * Result : We should have 91 cells
     */
    @Test
    public void testParseWikiTextNbCell5() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Comparison_of_PSA_systems&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        Iterator it;
        int nbcell = 0;

        for (Table tab : tabs) {
            it = tab.getContent().values().iterator();
            while (it.hasNext()) {
                String[] cells = (String[]) it.next();
                nbcell += cells.length;
            }
        }
        assertEquals("We should have 91 cells", 91, nbcell);
    }

    /**
     * Case : Test number of cells in a page with CSV problems
     * Result : We should have 132 cells
     */
    @Test
    public void testParseWikiTextNbCell6() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Comparison_of_S.M.A.R.T._tools&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        Iterator it;
        int nbcell = 0;

        for (Table tab : tabs) {
            it = tab.getContent().values().iterator();
            while (it.hasNext()) {
                String[] cells = (String[]) it.next();
                nbcell += cells.length;
            }
        }
        assertEquals("We should have 132 cells", 132, nbcell);
    }
}
