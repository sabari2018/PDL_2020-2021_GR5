package model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 */
public abstract class Parser {

    private String textToParse;
    private int nbColumns;
    private int nbRows;

    /**
     * @return textToParse attribute
     */
    public String getTextToParse() {
        return textToParse;
    }

    /**
     * @param newTextToParse the new value for the attribute newTextToParse
     */
    public void setTextToParse(final String newTextToParse) {
        this.textToParse = newTextToParse;
    }

    /**
     * @return nbColumns attribute
     */
    public int getNbColumns() {
        return nbColumns;
    }

    /**
     * @param newNbColumns the new value for newNbColumns attribute
     */
    public void setNbColumns(final int newNbColumns) {
        this.nbColumns = newNbColumns;
    }

    /**
     * @return nbRows attribute
     */
    public int getNbRows() {
        return nbRows;
    }

    /**
     * @param newNbRows the new value for newNbRows attribute
     */
    public void setNbRows(final int newNbRows) {
        this.nbRows = newNbRows;
    }

    /**
     * @param url the page url
     * @return the page
     */
    public Document getPageFromUrl(String url) {
        Document page = null;
        try {
            page = Jsoup.connect(url).maxBodySize(0).get();
        } catch (Exception e) {

        }
        return page;
    }
}
