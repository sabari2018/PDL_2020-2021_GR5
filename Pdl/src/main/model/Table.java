package model;

import java.util.HashMap;

/**
 * A public type which represents a Wikipedia table
 */

public class Table {

    private HashMap <Integer, String[]> content;
    private String title;
    private String extractionType;
    private int numTable;

    public Table (HashMap<Integer,String []> content, String title, String extractionType, int numTable) {
        this.content = content;
        this.title = title;
        this.extractionType = extractionType;
        this.numTable = numTable;
    }

    /**
     *
     * @return the content of HTML page in a HashMap
     */
    public HashMap getContent () {
        return this.content;
    }

    /**
     *
     * @return Wikipedia title page
     */
    public String getTitle () {
        return this.title;
    }

    /**
     *
     * @return the extraction type is html either wikitext
     */
    public String getExtractionType () {
        return this.extractionType;
    }

    /**
     *
     * @return position of the table in the Wikipedia page.
     *          For example is the first table in the page this method returns 1
     */
    public int getNumTable () {
        return this.numTable;
    }
}
