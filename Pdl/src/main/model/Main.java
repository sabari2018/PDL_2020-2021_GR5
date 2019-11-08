package model;

import java.io.IOException;
import model.ParserHTML;

public class Main {

   static  ProcessWikiUrl processWikiUrl;

    public static void main(String[] args) throws IOException {
        processWikiUrl.addWikiUrlFromFile("wikiurls", false, "en");
    }

}
