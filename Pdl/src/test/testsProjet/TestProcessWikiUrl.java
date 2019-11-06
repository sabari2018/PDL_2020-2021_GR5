package testsProjet;

import model.ProcessWikiUrl;
import org.junit.Test;

public class TestProcessWikiUrl {

    ProcessWikiUrl processWikiUrl;

    @Test
    public void testProcessWikiUrl(){
        processWikiUrl = new ProcessWikiUrl();

        processWikiUrl.addWikiUrlFromFile("wikiurls", false, "en");

        for(int i = 0; i < processWikiUrl.getListWikiUrl().size(); i++){
            System.out.println(processWikiUrl.getListWikiUrl().get(i).getHtmlUrl());
            System.out.println(processWikiUrl.getListWikiUrl().get(i).getWikiTextUrl());
        }
    }

}
