package model;

import java.util.HashMap;

/**
 * A public type which represents a Wikipedia table.
 */

public class Table {

    /**
     * In this HashMap : key = number of the table's row.
     * value = table of String. One case of this table
     * corresponds to the content of one cell of the current row's.
     */
    private HashMap<Integer, String[]> content;
    private String title;
    private String extractionType;
    private int numTable;

    public Table(HashMap<Integer, String[]> content, String title, String extractionType, int numTable) {
        this.content = content;
        this.title = title;
        this.extractionType = extractionType;
        this.numTable = numTable;
    }

    /**
     * Empty constructor.
     */
    public Table() {
        this.content = new HashMap<Integer, String[]>();
    }

    /**
     * @param title          title of the wikipedia page which contains the table.
     *                       It will be used in {@link Converter} to form the csv fil's name.
     * @param extractionType wikitext ot html
     * @param numTable       number table's number in the wikipedia page
     */
    public Table(final String title, final String extractionType, final int numTable) {
        this.content = new HashMap<Integer, String[]>();
        this.title = title;
        this.extractionType = extractionType;
        this.numTable = numTable;
    }

    /**
     * @return the content of HTML page in a HashMap
     */
    public HashMap getContent() {
        return this.content;
    }

    /**
     * @param content new content
     */
    public void setContent(HashMap<Integer, String[]> content) {
        this.content = content;
    }

    /**
     * @return Wikipedia title page
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @return the extraction type is html either wikitext
     */
    public String getExtractionType() {
        return this.extractionType;
    }

    /**
     * @return position of the table in the Wikipedia page.
     * For example is the first table in the page this method returns 1
     */
    public int getNumTable() {
        return this.numTable;
    }

    /**
     * Return the content of the current table
     */
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("=======TABLE=======\n");
        HashMap<Integer, String[]> tbl2 = this.getContent();
        for (Integer i : tbl2.keySet()) {
            ret.append("*****ROW*****\n");
            for (String str : tbl2.get(i)) {
                ret.append(str).append("\n");
            }
            ret.append("*************\n");
        }
        ret.append("=========================\n");
        return ret.toString();
    }
}
