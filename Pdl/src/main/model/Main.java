package model;

import java.io.IOException;

public class Main {

   static  ProcessWikiUrl processWikiUrl = new ProcessWikiUrl();

    public static void main(String[] args) throws IOException {
        processWikiUrl.addWikiUrlFromFile("wikiurls", false, "en");

        processWikiUrl.parseHTML();
        processWikiUrl.parseWikiText();
        processWikiUrl.convert();
    }
}
