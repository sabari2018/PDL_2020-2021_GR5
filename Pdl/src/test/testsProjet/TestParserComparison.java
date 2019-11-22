package testsProjet;

import model.ParserHTML;
import model.ParserWikiText;
import model.ProcessWikiUrl;
import model.Table;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;

/**
 * Test class that compares the rendering Table of 2 parsers (WikiText and HTML)
 */

public class TestParserComparison {

    private ProcessWikiUrl processWikiUrl;
    private ParserHTML parserHTML = new ParserHTML();
    private ParserWikiText parserWikiText = new ParserWikiText();

    int nbTabHTML;
    int nbTabHTMLCorrect;
    int nbTabWikiText;
    int nbTabWikiTextCorrect;

    /**
     * We recover the urls from the file
     */
    @Before
    public void setUp(){
        //Récupération des urls
        processWikiUrl = new ProcessWikiUrl();
        processWikiUrl.addWikiUrlFromFile("wikiurls", false, "en");
    }

    /**
     * Table number comparison test retrieved with the HTML parser
     * With the correct number of table
     */
    @Test
    public void TestCompareNbTabHTML(){

        for(int i = 0; i < processWikiUrl.getListWikiUrl().size() ; i++){

            parserHTML.setUrlHtml(processWikiUrl.getListWikiUrl().get(i).getHtmlUrl());
            ArrayList<Table> currentPageTables = parserHTML.parseHtml();

            for(int j = 0; j < currentPageTables.size(); j++){

                //compte le nombre de tableau
                nbTabHTML ++;

                Iterator it = currentPageTables.get(j).getContent().values().iterator();
                boolean nbCellsOk = true;
                int nbCell = 0;
                String[] cells = (String[]) it.next();
                nbCell = cells.length;

                while (it.hasNext()) {
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
        int pourcentageTabValid = nbTabHTMLCorrect*100/nbTabHTML;
        System.out.println("nombre tab hmtl correcte / nombre tab html = " + pourcentageTabValid +"%");
        assertEquals("On devrait avoir 100%", 100, pourcentageTabValid);
    }

    @Test
    public void TestCompareNbTabWikiText(){
        //On parse les tableaux des pages en WikiText
        //pour chaque page
        for(int i = 0; i < processWikiUrl.getListWikiUrl().size(); i++){
            //Traitement des pages
            parserWikiText.setUrlWikiText(processWikiUrl.getListWikiUrl().get(i).getWikiTextUrl());
            ArrayList<Table> currentPageTables = parserWikiText.parseWikiText();

            //pour chaque tableau
            for(int j = 0; j < currentPageTables.size(); j++){
                //augmentation du nb de tab
                nbTabWikiText ++;
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
                    nbTabWikiTextCorrect ++;
                }
            }
        }

        System.out.println("nombre tab wikitext " + nbTabWikiText);
        System.out.println("nombre tab wikitext correcte " + nbTabWikiTextCorrect);
        int pourcentageTabValid = nbTabWikiTextCorrect*100/nbTabWikiText;
        System.out.println("nombre tab wikitext correcte / nombre tab wikitext = " + pourcentageTabValid +"%");
        assertEquals("On devrait avoir 100%", 100, pourcentageTabValid);

    }
}
