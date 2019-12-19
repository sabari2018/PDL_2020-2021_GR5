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

    private int nbTabHTML;
    private int nbTabHTMLCorrect;
    private int nbTabWikiText;
    private int nbTabWikiTextCorrect;

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
    public void testCompareNbTabHTML(){

        for(int i = 0; i < processWikiUrl.getListWikiUrl().size() ; i++){

            parserHTML.setUrlHtml(processWikiUrl.getListWikiUrl().get(i).getHtmlUrl());
            ArrayList<Table> currentPageTables = parserHTML.parseHtml();

            for(int j = 0; j < currentPageTables.size(); j++){
                //Count number of table
                nbTabHTML ++;

                Iterator it = currentPageTables.get(j).getContent().values().iterator();
                boolean nbCellsOk = true;
                int nbCell = 0;
                //ligne 1 = reference line
                String[] cells = (String[]) it.next();
                nbCell = cells.length;

                while (it.hasNext()) {
                    String[] cells1 = (String[]) it.next();
                    if (cells1.length != nbCell){
                        nbCellsOk = false;
                    }
                }
                if (nbCellsOk){
                    nbTabHTMLCorrect ++;
                }
            }
        }
        int pourcentageTabValid = nbTabHTMLCorrect*100/nbTabHTML;
        assertEquals("The percentage must be 100%", 100, pourcentageTabValid);
    }

    /**
     * Table number comparison test retrieved with the WikiText parser
     * With the correct number of table
     */
    @Test
    public void TestCompareNbTabWikiText(){
        for(int i = 0; i < processWikiUrl.getListWikiUrl().size(); i++){

            parserWikiText.setUrlWikiText(processWikiUrl.getListWikiUrl().get(i).getWikiTextUrl());
            ArrayList<Table> currentPageTables = parserWikiText.parseWikiText();

            for(int j = 0; j < currentPageTables.size(); j++){
                //Count number of table
                nbTabWikiText ++;

                Iterator it = currentPageTables.get(j).getContent().values().iterator();
                boolean nbCellsOk = true;
                int nbCell = 0;
                //ligne 1 = reference line
                String[] cells = (String[]) it.next();
                nbCell = cells.length;

                while (it.hasNext()) {
                    String[] cells1 = (String[]) it.next();
                    if (cells1.length != nbCell){
                        nbCellsOk = false;
                    }
                }

                if (nbCellsOk){
                    nbTabWikiTextCorrect ++;
                }
            }
        }
        int pourcentageTabValid = nbTabWikiTextCorrect*100/nbTabWikiText;
        assertEquals("The percentage must be 100%", 100, pourcentageTabValid);

    }
}
