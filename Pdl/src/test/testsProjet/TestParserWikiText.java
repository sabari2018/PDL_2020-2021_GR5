package testsProjet;

import model.ParserWikiText;
import model.Table;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class TestParserWikiText {

    String urlWikiText;
    ParserWikiText pwt;

    /**
     * Tests parser wikitext :
     *      - bon nb de cell(s)
     *      - bon contenu
     *
     * - cell html ave  ligne(s) regroupee()s (Comment est compte le bon nombre si les lignes sont regroupees ?)
     * - cell html avec colonnes(s) regroupee(s) (Comment est compte le bon nombre si les colonnes sont regroupees ?)
     * - contenu avec seulement une image ? ou des images ? ou image(s) et texte ?
     * - contenu avec une liste ?
     * - contenu avec des icons ?
     * - contenu avec des liens ?
     * - contenu avec texte contenant des accents ?
     * - case avec le texte "Les données manquantes sont à compléter." ?
     * - contenu avec un texte avec un nombre pour acceder aux notes (lien vers la note) ?
     * - contenu tableau avec des cases vides et d'autres non vides
     * - contenu si ya des tris croissants ou dec
     */

    //tests tableaux

    /**
     * Pas de tableau dans la page
     */
    @Test
    public void testParseWikiTextNoTab() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Kyrkjedalshalsen_Saddle&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        assertEquals("On devrait avoir 0 tableau", 0, tabs.size(), tabs.size());
    }

    /**
     * Test si 1 tab dans la page
     */
    @Test
    public void testParseWikiText1Tab() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Comparison_of_Chernobyl_and_other_radioactivity_releases&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        assertEquals("On devrait avoir 1 tableau", 1, tabs.size());
    }

    /**
     * Test si plusieurs tab dans la page
     */
    @Test
    public void testParseWikiText8Tab() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Comparison_between_Esperanto_and_Ido&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        assertEquals("On devrait avoir 8 tableaux", 8, tabs.size());
    }

    /**
     * Tableau sans la mention wikitable : pas de tableau retourne
     */
    @Test
    public void testParseWikiTextSsWikitable() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=2011_Intersport_Heilbronn_Open_%E2%80%93_Singles&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        assertEquals("On devrait avoir 0 tableau", 0, tabs.size());
    }

    /**
     * Tableau vide ou avec une seule case vide
     */
    /*
    @Test
    public void testParseWikiTextTabVide(){
        urlWikiText = "";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        assertEquals("On devrait avoir 0 tableau", 0, tabs.size());
    }
    */

    /**
     * Tableau avec une seule case pleine
     */
    /*
    @Test
    public void testParseWikiTextTabVide(){
        urlWikiText = "";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        assertEquals("On devrait avoir 0 tableau", 0, tabs.size());
    }
    */

    //Tests lignes

    /**
     * Test nb de ligne dans une page avec un tab
     */
    @Test
    public void testParseWikiTextNbLign1() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Comparison_of_Chernobyl_and_other_radioactivity_releases&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        int nbligne = 0;
        for (Table tab : tabs
        ) {
            nbligne = tab.getContent().size();
        }
        assertEquals("On devrait avoir 5 lignes", 5, nbligne);
    }

    /**
     * Test nb de ligne dans une page avec un tab avec lignes regroupees
     */
    @Test
    public void testParseWikiTextNbLign2() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Turn-To&action=edit";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        int nbligne = 0;
        for (Table tab : tabs
        ) {
            nbligne = tab.getContent().size();
        }
        assertEquals("On devrait avoir 16 lignes", 16, nbligne);
    }

    /**
     * Test nb de ligne dans une page avec un tab avec colonnes regroupees
     */
    @Test
    public void testParseWikiTextNbLign3() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Shinichi_Kawano&action=edit&editintro=Template:BLP_editintro";
        pwt = new ParserWikiText();
        pwt.setUrlWikiText(urlWikiText);
        ArrayList<Table> tabs = pwt.parseWikiText();
        int nbligne = 0;
        for (Table tab : tabs
        ) {
            nbligne = tab.getContent().size();
        }
        assertEquals("On devrait avoir 9 lignes", 9, nbligne);
    }

    //TestsCells
    /**
     * Test nb de cell dans une page avec un tab
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
        //System.out.println(bool);
        assertEquals("On devrait avoir 10 cellules", 10, nbcell);
    }

    /**
     * Test nb de cell dans une page avec un tab avec lignes regroupées
     */
    @Test
    public void testParseWikiTextNbCell2() {
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Turn-To&action=edit";
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
        //System.out.println(bool);
        assertEquals("On devrait avoir 30 cellules", 30, nbcell);
    }

    /**
     * Test nb de cell dans une page avec un tab avec colonnes regroupées
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
        //System.out.println(bool);
        assertEquals("On devrait avoir 30 cellules", 79, nbcell);
    }


    //-----------------------------------------------------------------------------------------------------

    /**
     * Tableau avec petites images de drapeau
     */
    /*
    @Test
    public void testParseWikiText2(){
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=CSM_Ploie%C8%99ti_(women%27s_handball)&action=edit";
        pwt = new ParserWikiText(urlWikiText);
        //test
    }
    */

    /**
     * 3 tableaux : 2 avec des accents + virgules, 1 avec cellules regroupees + accents + virgules
     */
    /*
    @Test
    public void testParseWikiText3(){
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Comparison_between_Esperanto_and_Interlingua&action=edit";
        pwt new ParserWikiText(urlWikiText);
        //test
    }
    */

    /**
     * 8 tableaux : le 1er contient une case vide + accents
     */
    /*
    @Test
    public void testParseWikiText4(){
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Comparison_between_Esperanto_and_Ido&action=edit";
        pwt new ParserWikiText(urlWikiText);
        //test
    }
    */

    /**
     * 3 tableaux : dans les lignes de titre il y a le tri croissant et decroissant
     */
    /*
    @Test
    public void testParseWikiText5(){
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Comparison_of_Afrikaans_and_Dutch&action=edit";
        pwt new ParserWikiText(urlWikiText);
        //test
    }
     */

}
