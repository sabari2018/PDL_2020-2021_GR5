package testsProjet;

import model.Table;

import model.ParserHTML;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Test the html parsing of a html url
 */

public class TestParserHTML {


    /**
     * Creation of test url and test titles
     */

    ParserHTML p = new ParserHTML();

    /**
     * URL
     */
    String url = "https://en.wikipedia.org/wiki/Comparison_between_Esperanto_and_Ido";
    String urlTree = "https://en.wikipedia.org/wiki/2011_Intersport_Heilbronn_Open_%E2%80%93_Singles";

    /**
     * Raw HTML code
     * Include wikitable class, rowspan and colspan
     */

    /**
     * Contains 1 table
     */
    String codeTableHTML = "<table class=\"wikitable\">" +
            "<tbody><tr><th colspan=\"2\">Esperanto</th><th colspan=\"2\">Interlingua</th>" +
            "<th rowspan=\"2\">English</th></tr><tr><th>Preferred form</th><th>Alternative form</th>" +
            "<th>Preferred Form</th><th>Alternative form</th></tr><tr><td colspan=\"2\"><b>san</b>a" +
            "</td><td colspan=\"2\"><b>san</b></td><td>healthy</td></tr>" +"</tbody></table>";
    /**
     * Contains 2 tables in wikitable class
     */
    String codeDeuxTablesHTML = "<table class=\"wikitable\">" +
            "<tbody><tr><th colspan=\"2\">tableau 1</th>" +
            "<th colspan=\"2\">Interlingua</th><th rowspan=\"2\">English</th></tr><tr><th>Preferred form" +
            "</th><th>Alternative form</th><th>Preferred Form</th><th>Alternative form</th></tr><tr>" +
            "<td colspan=\"2\"><b>san</b>a</td><td colspan=\"2\"><b>san</b></td><td>healthy" +
            "</td></tr>" +"</tbody></table>"+
            "<p>Blablabla</p>"+
            "<table class=\"wikitable\"><tbody><tr><th>Tableau 2</th><th style=\"width: 40%\">tableau 2"+
            "</th><th style=\"width: 40%\">Interlingua</th></tr><tr><th>Type</th><td>"+
            "<a href=\"/wiki/Constructed_language#Schematic_languages\" title=\"Constructed language\">"+
            "schematic</a>;<br />designed to be easy to learn</td><td>"+
            "<a href=\"/wiki/Constructed_language#Naturalistic_languages\" title=\"Constructed language\">"+
            "naturalistic</a>;<br />designed to be easy to understand to as many people as possible"+
            "</td></tr>";
    /**
     * HTML code : 1 table with wikitable class and another without
     */
    String codeDeuxTablesHTML2 = "<table class=\"wikitable\">" +
            "<tbody><tr><th colspan=\"2\">Esperanto</th>" +
            "<th colspan=\"2\">Interlingua</th><th rowspan=\"2\">English</th></tr><tr><th>Preferred form" +
            "</th><th>Alternative form</th><th>Preferred Form</th><th>Alternative form</th></tr><tr>" +
            "<td colspan=\"2\"><b>san</b>a</td><td colspan=\"2\"><b>san</b></td><td>healthy" +
            "</td></tr>" +"</tbody></table>"+
            "<p>Blablabla</p>"+
            "<table><tbody><tr><th>Aspect</th><th style=\"width: 40%\">Esperanto"+
            "</th><th style=\"width: 40%\">Interlingua</th></tr><tr><th>Type</th><td>"+
            "<a href=\"/wiki/Constructed_language#Schematic_languages\" title=\"Constructed language\">"+
            "schematic</a>;<br />designed to be easy to learn</td><td>"+
            "<a href=\"/wiki/Constructed_language#Naturalistic_languages\" title=\"Constructed language\">"+
            "naturalistic</a>;<br />designed to be easy to understand to as many people as possible"+
            "</td></tr>";
    /**
     * HTML code of a row (only the contains of one row between <tr></tr>)
     */

    String row = "<th colspan=\"2\">row 1, cell 1</th>"+
                 "<td>cell 2</td>" +
                 "<td rowspan=\"2\">\"cell 3\"</td>";


    /**
     * Situation : test ParseHTML method from an url -> https://en.wikipedia.org/wiki/Comparison_between_Esperanto_and_Ido
     * Result : Read and count the number of tables from a page
     *          Expected 8 tables
     */

    @Test
    public void testParseHtml(){
        p.setUrlHtml(url);
        ArrayList<Table> result = p.parseHtml();
        assertEquals("Expected 8 tables from the url gave",8,result.size());
    }


    /**
     * Situation : test ParseHTML method with an url -> https://en.wikipedia.org/wiki/2011_Intersport_Heilbronn_Open_%E2%80%93_Singles
     *              The table in this page looks like a tree shape
     * Result : Here nothing is expected because the table isn't a 'wikitable' class
     */
    @Test
    public void testParseHtmlTree(){
        p.setUrlHtml(urlTree);
        ArrayList<Table> result = p.parseHtml();
        assertEquals("Expected 0 table because the table into this page isn't a wikitable class",0,result.size());
    }


    /**
     * Situation : test getTablesFromPage with a raw HTML code, this code is about one table with wikitable class
     * Result : Expected 1 table
     */
    @Test
    public void testGetTablesFromPage(){
        ArrayList<String> result = p.getTablesFromPage(codeTableHTML);
        assertEquals("Expected 1 table",1,result.size());
    }


    /**
     * Situation : test getTablesFromPage with a raw HTML code, this code is about two tables with wikitable classes
     * Result : Expected 2 tables
     */
    @Test
    public void testGetTablesFromPage2(){
        ArrayList<String> result = p.getTablesFromPage(codeDeuxTablesHTML);
        assertEquals("Expected 2 tables",2,result.size());
    }


    /**
     * Situation : test getTablesFromPage with a raw HTML code, this code is about two tables with one wikitable class
     *              and another without
     * Result : Expected 1 table
     */
    @Test
    public void testGetTablesFromPage3(){
        ArrayList<String> result = p.getTablesFromPage(codeDeuxTablesHTML2);
        assertEquals("Expected 1 table because the 2nd isn't a wikitable class",1,result.size());
    }

    /**
     * Situation : We wrote a string to test escapeComasAndQuotes's method
     * Result : the test have to be between "" and a " are doubled
     */
    @Test
    public void testEscapeComasAndQuotes(){
        String test = "\",sfg\"\"dfg_,rf,\"fi\"':";
        assertEquals("Expected : \"\"\",sfg\"\"\"\"dfg_,rf,\"\"fi\"\"':\"","\"\"\",sfg\"\"\"\"dfg_,rf,\"\"fi\"\"':\"",p.escapeComasAndQuotes(test));
    }

    /**
     * Situation : Test getRowsFromTable with a raw HTML code. This code is about one wikiclass table
     * Result : Count number of rows into the table -> expected 3 rows
     */
    @Test
    public void testGetRowsFromTable(){
        ArrayList<String> result = p.getRowsFromTable(codeTableHTML);
        assertEquals("Expected 3 rows",3,result.size());

    }

    /**
     * Situation : Test getCellsFromRow with a raw HTML code. This code is about one raw from a table
     *              This code contains only the code between <tr></tr>
     *              Only 1 line can be tested
     * Result : Expected 3 cells
     */
    @Test
    public void testGetCellsFromRow(){
        ArrayList<String> result = p.getCellsFromRow(row);
        assertEquals("Expected 3 cells",3,result.size());
    }
}
