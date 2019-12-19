package testsProjet;

import model.ParserWikiText;
import model.Table;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
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
     * No table in the page
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
     * Test if 1 table in the page
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
     * Test if multiple tables in the page
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
     * Table without the mention "wikitable": no table returned
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
     * Test rows number in a page with a table
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
     * Test rows number in a page with a table with grouped rows
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
     * Test rows number in a page with a table with grouped columns
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
     * Test rows number in a page with a table with CSV problems
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
     * Test rows number in a page with a table with 2 title ligns
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
     * Test rows number in a page with a table with CSV problems
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
     * Test number of cells in a page with a table
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
     * Test number of cells in a page with a table with grouped rows
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
     * Test number of cells in a page with a table with grouped columns
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
     * Test number of cells in a page with CSV problems
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
     * Test number of cells in a page with 2 title ligns
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
     * Test number of cells in a page with CSV problems
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

    //Cells content

    /**
     * Cells content in a page with a tab
     */
    /*
    @Test
    public void testParseWikiTextContenuCells1() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Comparison_of_Chernobyl_and_other_radioactivity_releases&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        Iterator it;

        for (Table tab : tabs) {
            it = tab.getContent().values().iterator();
            while (it.hasNext()) {
                String[] cells = (String[]) it.next();
                for (String cell: cells) {
                    System.out.println(cell);
                }
            }
        }
    }
    */

    /**
     * Cells content in a page with a tab with small images & texts
     */
    /*
    @Test
    public void testParseWikiTextContenuCells2() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=CSM_Ploie%C8%99ti_(women%27s_handball)&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        Iterator it;

        for (Table tab : tabs) {
            it = tab.getContent().values().iterator();
            while (it.hasNext()) {
                String[] cells = (String[]) it.next();
                for (String cell: cells) {
                    System.out.println(cell);
                }
            }
        }
    }

     */

    /**
     * Cells content in a page with a tab with images
     */
    /*
    @Test
    public void testParseWikiTextContenuCells3() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Comparison_of_Toyota_hybrids&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        Iterator it;

        for (Table tab : tabs) {
            it = tab.getContent().values().iterator();
            while (it.hasNext()) {
                String[] cells = (String[]) it.next();
                for (String cell : cells) {
                    System.out.println(cell);
                }
            }
        }
    }

     */

    /**
     * Cells content in a page with a tab starting with an empty title line
     */
    /*
    @Test
    public void testParseWikiTextContenuCells4() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Comparison_of_US_and_Chinese_Military_Armed_Forces&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        Iterator it;

        for (Table tab : tabs) {
            it = tab.getContent().values().iterator();
            while (it.hasNext()) {
                String[] cells = (String[]) it.next();
                for (String cell : cells) {
                    System.out.println(cell);
                }
            }
        }
    }

     */

    /**
     * Cells content in a page with a tab with increasing and decreasing sorting and access number to a note
     */
    /*
    @Test
    public void testParseWikiTextContenuCells5() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Comparison_of_Windows_Vista_and_Windows_XP&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        Iterator it;

        for (Table tab : tabs) {
            it = tab.getContent().values().iterator();
            while (it.hasNext()) {
                String[] cells = (String[]) it.next();
                for (String cell : cells) {
                    System.out.println(cell);
                }
            }
        }
    }

     */

    /**
     * Cells content in a page with a tab with empty cells & others not empty
     */
    /*
    @Test
    public void testParseWikiTextContenuCells6() {
        urlWikiText = "https://en.wikipedia.org/wiki/Comparison_of_Intel_processors";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        Iterator it;

        for (Table tab : tabs) {
            it = tab.getContent().values().iterator();
            while (it.hasNext()) {
                String[] cells = (String[]) it.next();
                for (String cell : cells) {
                    System.out.println(cell);
                }
            }
        }
    }
    */

    /**
     * Cells content in a page with a tab with a list
     */
    /*
    @Test
    public void testParseWikiTextContenuCells7() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Comparison_of_baseball_and_cricket&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        Iterator it;

        for (Table tab : tabs) {
            it = tab.getContent().values().iterator();
            while (it.hasNext()) {
                String[] cells = (String[]) it.next();
                for (String cell : cells) {
                    System.out.println(cell);
                }
            }
        }
    }
    */

    /**
     * Cells content in a page with a tab with accent in the text
     */
    /*
    @Test
    public void testParseWikiTextContenuCells8() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Comparison_of_karate_styles&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        Iterator it;

        for (Table tab : tabs) {
            it = tab.getContent().values().iterator();
            while (it.hasNext()) {
                String[] cells = (String[]) it.next();
                for (String cell : cells) {
                    System.out.println(cell);
                }
            }
        }
    }
    */
}
