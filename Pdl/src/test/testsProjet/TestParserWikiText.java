package testsProjet;

import model.ParserWikiText;
import org.junit.Before;
import org.junit.Test;

public class TestParserWikiText {

    String urlWikiText;
    ParserWikiText pwt;

    /**
     * Tests parser wikitext :
     * Pour chacun des cas on teste :
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

    /**
     * Pas de tableau dans la page
     */
    /*
    @Test
    public void testParseWikiTextNoTab(){
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=Kyrkjedalshalsen_Saddle&action=edit";
        pwt = new ParserWikiText(urlWikiText);
        //test
    }
    */

    /**
     * Tableau avec une ligne titre
     */
    /*
    @Test
    public void testParseWikiText1(){
            urlWikiText = "https://en.wikipedia.org/w/index.php?title=Hydroponic_Garden_(album)&action=edit";
            pwt = new ParserWikiText(urlWikiText);
            //test
    }
    */

    /**
     * Tableau avec petits icon?/image? de drapeau
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
     * Tableau arbre : non traité ?
     */
    /*
    @Test
    public void testParseWikiTextArbre(){
        urlWikiText = "https://en.wikipedia.org/w/index.php?title=2011_Intersport_Heilbronn_Open_%E2%80%93_Singles&action=edit";
        pwt new ParserWikiText(urlWikiText);
    }
    */

}
