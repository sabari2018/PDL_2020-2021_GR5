package testsProjet;

import model.ParserHTML;
import model.ProcessWikiUrl;
import model.Table;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

/**
 * Classe de test qui compare le rendu Table des 2 parsers (WikiText et HTML)
 */

public class TestParserComparison {

    private ProcessWikiUrl processWikiUrl;
    private ParserHTML parserHTML = new ParserHTML();

    int nbTabHTML;
    int nbTabHTMLCorrect;
    int nbTabWikiText;
    int nbTabWikiTextCorrect;

    @Before
    public void setUp(){
        //Récupération des urls
        processWikiUrl = new ProcessWikiUrl();
        processWikiUrl.addWikiUrlFromFile("wikiurlsTest", false, "en");
    }

    @Test
    public void TestCompareNbTabHTML(){
        //On parse les tableaux des pages en HTML
        //pour chaque page
        for(int i = 0; i < processWikiUrl.getListWikiUrl().size() ; i++){
            //Traitement des pages
            parserHTML.setUrlHtml(processWikiUrl.getListWikiUrl().get(i).getHtmlUrl());
            ArrayList<Table> currentPageTables = parserHTML.parseHtml();
            //pour chaque tableau
            for(int j = 0; j < currentPageTables.size(); j++){
                //augmentation du nb de tab
                nbTabHTML ++;
                //iterateur sur le contenu HashMap du tab
                Iterator it = currentPageTables.get(j).getContent().values().iterator();
                //init nombre de cell
                int nbCell = 0;
                //meme nb cells
                boolean nbCellsOk = true;
                //on récupère la ligne 1 en ref
                String[] cells = (String[]) it.next();
                nbCell = cells.length;
                //pour les autres lignes
                while (it.hasNext()) {
                    //on récupere la ligne
                    String[] cells1 = (String[]) it.next();
                    //on regarde si c'est différent à la ligne de ref
                    if (cells1.length != nbCell){
                        nbCellsOk = false;
                    }
                }
                //si toute cells egale par ligne on augment nb tab correct
                if (nbCellsOk == true){
                    nbTabHTMLCorrect ++;
                }
            }
        }

        System.out.println("nombre tab hmtl " + nbTabHTML);
        System.out.println("nombre tab hmtl correcte " + nbTabHTMLCorrect);
        int pourcentageTabValid = nbTabHTMLCorrect/nbTabHTML*100;
        System.out.println("nombre tab hmtl correcte / nombre tab html = " + pourcentageTabValid +"%");
        assertEquals("On devrait avoir 100%", 100, pourcentageTabValid);
    }

    @Test
    public void TestCompareNbTabWikiText(){

    }
}
