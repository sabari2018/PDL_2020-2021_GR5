package model;

public class WikiUrl {

    private String htmlUrl;
    private String wikiTextUrl;

    public WikiUrl(String newHtmlUrl){
        htmlUrl = newHtmlUrl;

        //checkvalidity
        String[] separateur = htmlUrl.split("https://en.wikipedia.org/wiki/");
        String title = separateur[1];
        wikiTextUrl = "https://en.wikipedia.org/w/index.php?title=" + title + "&action=edit";
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getWikiTextUrl() {
        return wikiTextUrl;
    }
}
