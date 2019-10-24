package testsProjet;

import model.WikiUrl;
import org.junit.Assert;
import org.junit.Test;

public class TestWikiUrl {
    WikiUrl wikiUrlTest;

    @Test
    public void testValidUrl(){
        try {
            wikiUrlTest = new WikiUrl("https://en.wikipedia.org/wiki/Young_Aviators");
            Assert.assertEquals("HTML url is not the good one", "https://en.wikipedia.org/wiki/Young_Aviators", wikiUrlTest.getHtmlUrl());
            Assert.assertEquals("Wiki Text url is not the good one", "https://en.wikipedia.org/w/index.php?title=Young_Aviators&action=edit", wikiUrlTest.getWikiTextUrl());
        }
        catch (IllegalArgumentException argException){
            Assert.assertTrue(argException.getMessage(), false);
        }
    }

    @Test
    public void testInvalidUrl(){
        String errorMsg = "";
        try{
            wikiUrlTest = new WikiUrl("htt://en.wipedia.org/ki/Yungviators");
        }
        catch (IllegalArgumentException argException){
            errorMsg = argException.getMessage();
        }
        Assert.assertEquals("We should have an IllegalArgumentException", "Url is not valid", errorMsg);
    }

    @Test
    public void testEmptyUrl(){
        String errorMsg = "";
        try{
            wikiUrlTest = new WikiUrl("");
        }
        catch (IllegalArgumentException argException){
            errorMsg = argException.getMessage();
        }
        Assert.assertEquals("We should have an IllegalArgumentException", "Url is not valid", errorMsg);
    }

    @Test
    public void testNullUrl(){
        String empty = null;
        String errorMsg = "";
        try{
            wikiUrlTest = new WikiUrl(empty);
        }
        catch (IllegalArgumentException argException){
            errorMsg = argException.getMessage();
        }
        Assert.assertEquals("We should have an IllegalArgumentException", "Url is not valid", errorMsg);
    }

    @Test
    public void testChineseUrl(){
        try {
            wikiUrlTest = new WikiUrl("https://zh.wikipedia.org/wiki/%E5%A5%A7%E7%88%BE%E6%B2%99%E6%B9%96");
            Assert.assertEquals("HTML url is not the good one", "https://zh.wikipedia.org/wiki/%E5%A5%A7%E7%88%BE%E6%B2%99%E6%B9%96", wikiUrlTest.getHtmlUrl());
            Assert.assertEquals("Wiki Text url is not the good one", "https://zh.wikipedia.org/w/index.php?title=%E5%A5%A7%E7%88%BE%E6%B2%99%E6%B9%96&action=edit", wikiUrlTest.getWikiTextUrl());
        }
        catch (IllegalArgumentException argException){
            Assert.assertTrue(argException.getMessage(), false);
        }
    }
}
