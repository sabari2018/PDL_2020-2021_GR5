package testsProjet;

import model.Parser;
import model.Table;
import org.junit.Test;

import model.ParserHTML;

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
    String url = "https://en.wikipedia.org/wiki/Comparison_between_Esperanto_and_Interlingua";
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
            "<tbody><tr><th colspan=\"2\">Esperanto</th>" +
            "<th colspan=\"2\">Interlingua</th><th rowspan=\"2\">English</th></tr><tr><th>Preferred form" +
            "</th><th>Alternative form</th><th>Preferred Form</th><th>Alternative form</th></tr><tr>" +
            "<td colspan=\"2\"><b>san</b>a</td><td colspan=\"2\"><b>san</b></td><td>healthy" +
            "</td></tr>" +"</tbody></table>"+
            "<p>Blablabla</p>"+
            "<table class=\"wikitable\"><tbody><tr><th>Aspect</th><th style=\"width: 40%\">Esperanto"+
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
     * @return the contents tables of this page and its number
     * 1 line = the contents of 1 cell
     * each table is seperate by =====Table===== and end by ============
     * each row is seperate by *****ROW***** and end by *************
     */
    @Test
    public void testParseHtml(){
        p.setUrlHtml(url);
        ArrayList<Table> result = p.parseHtml();
        assertEquals(3,result.size());
    }


    /**
     * @return nothing
     * Here nothing is expected because it isn't a 'wikitable' class
     */
    @Test
    public void testParseHtmlTree(){
        p.setUrlHtml(urlTree);
        ArrayList<Table> result = p.parseHtml();
        assertEquals(0,result.size());
    }


    /**
     * @return the number of tables into the HTML code
     * Expected 1 table
     */
    @Test
    public void testGetTablesFromPage(){
        ArrayList<String> result = p.getTablesFromPage(codeTableHTML);
        assertEquals(1,result.size());
    }


    /**
     * @return the number of tables into the HTML code
     * Expected 2 tables
     */
    @Test
    public void testGetTablesFromPage2(){
        ArrayList<String> result = p.getTablesFromPage(codeDeuxTablesHTML);
        assertEquals(2,result.size());
    }


    /**
     * @return the number of tables into the HTML code
     * Expected 1 table
     */
    @Test
    public void testGetTablesFromPage3(){
        ArrayList<String> result = p.getTablesFromPage(codeDeuxTablesHTML2);
        assertEquals(1,result.size());
    }

    /**
     * @return the number of tables into the HTML code
     * Expected 1 table
     * TODO tester avec un lien URL
     */
    @Test
    public void testGetTablesFromPageURLlink(){
        p.getHtmlPage();
        ArrayList<String> result = p.getTablesFromPage(codeDeuxTablesHTML2);
        assertEquals(1,result.size());
    }


    /**
     * Tests parser html :
     * Tester :
     *      - bon nb de ligne(s)
     *      - bon nb de colonne(s)
     *      - bon contenu
     *
     * - tableau sans titre
     * - tableau avec titre
     * - tableau html ave  ligne(s) regroupee()s (Comment est compte le bon nombre si les lignes sont regroupees ?)
     * - tableau html avec colonnes(s) regroupee(s) (Comment est compte le bon nombre si les colonnes sont regroupees ?)
     * - tableaux vides ?
     * - tableaux avec une seule case ? (titre ou non titre)
     * - case avec seulement une image ? ou des images ? ou image(s) et texte ?
     * - case avec une liste ?
     * - case avec des icons ?
     * - case avec des liens ?
     * - case avec texte contenant des accents ?
     * - case avec le texte "Les données manquantes sont à compléter." ?
     * - case avec un texte avec un nombre pour acceder aux notes (lien vers la note) ?
     * - tableau avec des cases vides et d'autres non vides
     * - tableau a double entree
     * - si ya des tris croissants ou dec
     */
}
