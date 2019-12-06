package testsProjet;

import model.Table;
//import org.jsoup.nodes.Document;


import model.ParserHTML;
import org.junit.Test;
//import org.junit.jupiter.api.Test;

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

    String row = "<tr><th colspan=\"2\">row 1</th><th colspan=\"2\">Interlingua</th>" +
            "<th rowspan=\"2\">English</th></tr><tr><th>Preferred form</th><th>Alternative form</th>" +
            "<th>Preferred Form</th><th>Alternative form</th></tr><tr><td colspan=\"2\"><b>san</b>a" +
            "</td><td colspan=\"2\"><b>san</b></td><td>healthy</td></tr>";

    String htmlInTable = "<table class=\"wikitable\">\n" +
            "<caption>Structure d’un document HTML\n" +
            "</caption>\n" +
            "<tbody><tr>\n" +
            "<th scope=\"col\">Source HTML\n" +
            "</th>\n" +
            "<th scope=\"col\">Modèle du document\n" +
            "</th></tr>\n" +
            "<tr>\n" +
            "<td><div class=\"mw-highlight mw-content-ltr\" dir=\"ltr\"><pre><span></span><span class=\"cp\">&lt;!DOCTYPE html PUBLIC &quot;-//IETF//DTD HTML 2.0//EN&quot;&gt;</span>\n" +
            "<span class=\"p\">&lt;</span><span class=\"nt\">html</span><span class=\"p\">&gt;</span>\n" +
            " <span class=\"p\">&lt;</span><span class=\"nt\">head</span><span class=\"p\">&gt;</span>\n" +
            "  <span class=\"p\">&lt;</span><span class=\"nt\">title</span><span class=\"p\">&gt;</span>\n" +
            "   Exemple de HTML\n" +
            "  <span class=\"p\">&lt;/</span><span class=\"nt\">title</span><span class=\"p\">&gt;</span>\n" +
            " <span class=\"p\">&lt;/</span><span class=\"nt\">head</span><span class=\"p\">&gt;</span>\n" +
            " <span class=\"p\">&lt;</span><span class=\"nt\">body</span><span class=\"p\">&gt;</span>\n" +
            "  Ceci est une phrase avec un <span class=\"p\">&lt;</span><span class=\"nt\">a</span> <span class=\"na\">href</span><span class=\"o\">=</span><span class=\"s\">&quot;cible.html&quot;</span><span class=\"p\">&gt;</span>hyperlien<span class=\"p\">&lt;/</span><span class=\"nt\">a</span><span class=\"p\">&gt;</span>.\n" +
            "  <span class=\"p\">&lt;</span><span class=\"nt\">p</span><span class=\"p\">&gt;</span>\n" +
            "   Ceci est un paragraphe où il n’y a pas d’hyperlien.\n" +
            "  <span class=\"p\">&lt;/</span><span class=\"nt\">p</span><span class=\"p\">&gt;</span>\n" +
            " <span class=\"p\">&lt;/</span><span class=\"nt\">body</span><span class=\"p\">&gt;</span>\n" +
            "<span class=\"p\">&lt;/</span><span class=\"nt\">html</span><span class=\"p\">&gt;</span>\n" +
            "</pre></div>\n" +
            "</td>\n" +
            "<td>\n" +
            "<div style=\"padding:0 0.25em;border:1px solid #393\">\n" +
            "<p>html\n" +
            "</p>\n" +
            "<div style=\"padding:0 0.25em;margin:0.25em;margin-left:1em;border:1px solid #393\">\n" +
            "<p>head\n" +
            "</p>\n" +
            "<div style=\"padding:0 0.25em;margin:0.25em;margin-left:1em;border:1px solid #393\">\n" +
            "<p>title\n" +
            "</p>\n" +
            "</div>\n" +
            "</td></tr></tbody></table>";
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
     * Expected 3 tables
     */
   /* @Test
    public void testGetTablesFromPageURLlink(){
        String html_page = p.getHtmlPage(); //ajouter un url temporaire dans gethtmlpage
        //System.out.println(p.getHtmlPage());
        ArrayList<String> result = p.getTablesFromPage(html_page);
        assertEquals(3,result.size());
    }*/

    /**
     * @return the test have to be between "" and a " are doubled
     */
    @Test
    public void testEscapeComasAndQuotes(){
        String test = "\",sfg\"\"dfg_,rf,\"fi\"':";
        System.out.println(p.escapeComasAndQuotes(test));
        assertEquals("\"\"\",sfg\"\"\"\"dfg_,rf,\"\"fi\"\"':\"",p.escapeComasAndQuotes(test));
    }

    /**
     * @return all code between <tr></tr> in a table
     */
    @Test
    public void testGetRowsFromTable(){
        ArrayList<String> result = p.getRowsFromTable(codeTableHTML);
        System.out.println(result);
        assertEquals(3,result.size());

    }


    /**
     * @return 10 cells contains
     * only 1 line can be analyze
     */
    @Test
    public void testGetCellsFromRow(){
        ArrayList<String> result = p.getCellsFromRow(row);
        System.out.println(result);
        assertEquals(10,result.size());

    }


    /**
     * @return 10 contenus de cellules
     * ne travail que sur seule ligne à la fois
     */
    @Test
    public void testParseSourceCodeExamples(){
        //String result = p.parseSourceCodeExamples(htmlInTable);
        //System.out.println(result);
        //assertEquals(10,result.size());
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
