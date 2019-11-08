package testsProjet;

import model.Parser;
import org.junit.Test;

import model.ParserHTML;

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
     * @return the contents tables of this page
     * 1 line = the contents of 1 cell
     * each table is seperate by =====Table===== and end by ============
     * each row is seperate by *****ROW***** and end by *************
     */
    @Test
    public void testParseHtml(){
        p.setUrlHtml("https://en.wikipedia.org/wiki/Comparison_between_Esperanto_and_Interlingua");
        p.parseHtml();
        //assertEquals("",p.parseHtml());
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
