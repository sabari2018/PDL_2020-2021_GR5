package model;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WikiUrl {

    private String htmlUrl;
    private String wikiTextUrl;

    /**
     * Constructor of WikiUrl :
     * with a given URl of a wikipedia page (HTML URL, not WikiText URL), test if it is valid and, if so, affect it to htmlUrl.
     * Then create the WikiText URL for the same page, test if it is valid and, if so, affect it to wikiTextUrl.
     * If either of the two Url are invalid, throw an IllegalArgumentException
     *
     * @param newHtmlUrl URL of a wikipedia page (HTML URL, not WikiText URL).
     * @throws IllegalArgumentException Exception throw if one of the URLs is invalid
     */
    public WikiUrl(String newHtmlUrl) throws IllegalArgumentException {
        testAndAffectUrl(newHtmlUrl, true);

        String[] titleSpliter = htmlUrl.split("wikipedia.org/wiki/");
        String title = titleSpliter[1];
        String[] languageSpliter = htmlUrl.split("https://");
        languageSpliter = languageSpliter[1].split(".wikipedia");
        String language = languageSpliter[0];
        String wikiTextUrlToTest = "https://"+ language + ".wikipedia.org/w/index.php?title=" + title + "&action=edit";

        testAndAffectUrl(wikiTextUrlToTest, false);
    }

    /**
     *
     * @return url of the html page
     */
    public String getHtmlUrl() {
        return htmlUrl;
    }

    /**
     *
     * @return url of the wikitext page
     */
    public String getWikiTextUrl() {
        return wikiTextUrl;
    }

    /**
     * Test a given Url of a wikipedia page (either HTML or a WikiText) and if valid, affect it to either htmlUrl or wikiTextUrl
     * If the URL is not valid, throw an IllegalArgumentException
     *
     * @param testedUrlString URL to test
     * @param isHTML Boolean defining if we test htmlUrl or wikiTextUrl
     */
    private void testAndAffectUrl(String testedUrlString, boolean isHTML){
        try {
            URL testedUrl = new URL(testedUrlString);
            HttpURLConnection connexion = (HttpURLConnection) testedUrl.openConnection();
            InputStream flux = connexion.getInputStream(); //créer l'exception
            flux.close();
            connexion.disconnect();

            if(isHTML){
                htmlUrl = testedUrlString;
            }
            else{
                wikiTextUrl = testedUrlString;
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("Url is not valid");
        }
    }
}
