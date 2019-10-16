package model;

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
}
