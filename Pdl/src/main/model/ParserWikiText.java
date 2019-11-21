package model;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hheinle
 * <p>
 * Parse wikiText and standardize it with the data structure {@link Table}.
 */
public class ParserWikiText extends Parser {

    private static final String START_WIKITABLE = "\\{\\|.*class=\"*.*wikitable.*\"*";
    private static final String END_WIKITABLE = "\\|}";
    private static final String KEY_WORD_WIKITABLE = "wikitable";
    private final Logger logger = Logger.getLogger(ParserWikiText.class);
    private final String HEAD_SEPARATOR = "\\|-";
    private final String ROW_SEPARATOR = "\\|\\-(\\n)(\\|)?";
    private final String CELL_SEPARATOR = "(\\n)*\\| ";

    private final String REGEX_COLSPAN = ".*colspan=\"(.*)\"";

    private String urlWikiText;
    private String titleOfCurrentPage;
    private ArrayList<String> wikiTextTables;
    private ArrayList<Table> standardizedTables;

    /**
     * Empty Constructor.
     */
    public ParserWikiText() {
        String log4jConfPath = "Pdl/resources/log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
        this.wikiTextTables = new ArrayList<String>();
    }

    /**
     * Set the url of the page we want to parse.
     * In the same time, it set the attribute textToParse
     *
     * @param urlWikiText new url to access
     */
    public void setUrlWikiText(final String urlWikiText) {
        this.urlWikiText = urlWikiText;
        this.standardizedTables = new ArrayList<Table>();
        Document doc = this.getPageFromUrl(this.urlWikiText);
        if (doc != null) {
            this.setTextToParse(doc.html());
            this.titleOfCurrentPage = doc.title();
        }
    }

    /**
     * From a urlWikiText url, access to the page and take the table(s) to put
     * them in a list of objects {@link Table}.
     *
     * @return a list of {@link Table}. It's length is the same as the number
     * of tables in the Wikipedia page
     */
    public ArrayList<Table> parseWikiText() {

        // extract all the tables of the Wikipedia page
        // the result put in wikiTextTables is still in wikiText
        this.wikiTextTables = extractTablesFromPage();

        // transform the table (format = String) into format = Table
        // and add an object Table to this.standardizedTables for each table
        int numTable = 1;
        for (String table : this.wikiTextTables) {
            Table standardizeTable = new Table(this.titleOfCurrentPage, "wikitext", numTable);
            standardizeTable.getContent().put(0, this.getTableHead(table));
            String[] rows = this.getTableRow(table);
            for (int i = 0; i < rows.length; i++) {
                standardizeTable.getContent().put(i + 1, this.getCells(rows[i]));
            }
            this.standardizedTables.add(standardizeTable);
            numTable++;
        }
        return this.standardizedTables;
    }

    /**
     * Extract the tables of the page.
     *
     * @return a list which contains the content of the tables
     */
    private ArrayList<String> extractTablesFromPage() {
        ArrayList<String> tablesFromPage = new ArrayList<String>();
        int nbWikiTables = this.countWikiTab();
        for (int i = 1; i <= nbWikiTables; i++) {
            String table = this.getTable();
            if (!table.trim().isEmpty()) {
                tablesFromPage.add(table);
            }
        }
        return tablesFromPage;
    }

    /**
     * Extract one table from the page.
     * As soon as a table is extracted, we remove it from
     * the text to parse.
     * Then, during the following extraction, we can get the following table
     * thanks to "indexOf(START_WIKITABLE)".
     *
     * @return the table
     */
    private String getTable() {
        String oneTable = "";
        int startTable = 0;
        int endTable = 0;
        Pattern p = Pattern.compile(START_WIKITABLE);
        Matcher m = p.matcher(this.getTextToParse());
        if (m.find()) {
            startTable = m.start();
            this.setTextToParse(this.getTextToParse().substring(startTable));
            startTable = 0;
        }
        Pattern p2 = Pattern.compile(END_WIKITABLE);
        Matcher m3 = p2.matcher(this.getTextToParse());
        if (m3.find()) {
            endTable = m3.start();
            if (endTable < startTable) {
                // sometimes, a table finish by "|} |}". So the following table
                // end before it's start
                logger.debug("The end of the table is before the start");
                m3.find();
                endTable = m3.start();
            }
        }
        oneTable = this.getTextToParse().substring(startTable + 2, endTable - 2);
        this.setTextToParse(this.getTextToParse().substring(endTable + 2));

        return oneTable;
    }

    /**
     * Gets the cells of the table's head.
     *
     * @param table the table we want the head
     * @return a table of String. One case of this table is a cell of
     * the table's head.
     */
    private String[] getTableHead(final String table) {
        String[] separator = table.split(HEAD_SEPARATOR);
        String[] tabfinal = new String[separator.length];
        ArrayList<String> list = new ArrayList<String>();
        if (separator[0].contains("!")) {
            list.add(separator[0]); // if the first split contains "!" which represents a column, we add it to the list
        }
        for (int i = 1; i < separator.length; i++) {
            list.add(separator[i]);
        }
        tabfinal = list.toArray(tabfinal);
        String head = tabfinal[0];
        this.setNbColumns(head.split("!").length - 1);
        String[] separatorOfHeadCells = head.split("!");
        String[] cellsHead = new String[this.getNbColumns()];
        for (int i = 0; i < this.getNbColumns(); i++) {
            cellsHead[i] = separatorOfHeadCells[i + 1]; // split[0] is empty so we don't take it
            if (cellsHead[i].contains("|")) { // delete the tags before the columns names
                String[] separator2 = cellsHead[i].split("\\| ");
                // Matcher matcher = Pattern.compile(REGEX_COLSPAN).matcher(separator2[0]);
                cellsHead[i] = separator2[1];
                cellsHead[i] = handleCommasInData(cellsHead[i]);
//                if (matcher.matches()) {
//                    int colspan = Integer.parseInt(matcher.group(1));
//                    for(int j = i; j <= colspan; j++){
//                        cellsHead[j] = ",";
//                    }
//                }
            }
        }
        this.setNbColumns(cellsHead.length);
        return cellsHead;
    }

    /**
     * Gets a row from a table.
     *
     * @param table the table (in wikitext)
     * @return a table of String. One case corresponds to
     * the content of one row.
     */
    private String[] getTableRow(final String table) {
        String[] rows = table.split(ROW_SEPARATOR);
        ArrayList<String> list = new ArrayList<String>();
        // from 1 because rows[0] is the head
        for (int i = 1; i < rows.length; i++) {
            // remove unwanted String
            rows[i] = rows[i].replaceAll("align=right", "");
            rows[i] = rows[i].replaceAll("align=left", "");
            rows[i] = rows[i].replaceAll("ref&gt;[^>]*/ref&gt;", "");
            rows[i] = rows[i].replaceAll("&lt;ref[^>]*/ref&gt;", "");
            rows[i] = rows[i].replaceAll("&lt;", "");
            rows[i] = rows[i].replaceAll("&gt;", "");
            rows[i] = rows[i].replaceAll("align=\"left\"", "");
            rows[i] = rows[i].replaceAll("br/&gt;", "");
            rows[i] = rows[i].replaceAll("&lt;br/&gt;", "");
            rows[i] = rows[i].replaceAll("&lt;ref&gt;", "");
            rows[i] = rows[i].replaceAll("&lt;br /&gt;", "");
            rows[i] = rows[i].replaceAll("br /&gt;", "");
            rows[i] = rows[i].replaceAll("&amp;nbsp;", "");
            rows[i] = rows[i].replaceAll("&amp", "");
            rows[i] = rows[i].replaceAll("Color[^>]*darkgray", "");
            rows[i] = rows[i].replaceAll("\\{\\{.*\\|", "");
            rows[i] = rows[i].replaceAll("}}", "");
            rows[i] = rows[i].replaceAll("\n", " ");
            if (!rows[i].isEmpty()) {
                list.add(rows[i]);
            }
        }
        String[] tableau = new String[list.size()];
        tableau = list.toArray(tableau);
        return tableau;
    }

    /**
     * Gets the cells of the given row.
     *
     * @param row the row
     * @return a table of String. One case of this table corresponds
     * to the content of the row's cells.
     */
    private String[] getCells(final String row) {
        String[] ret = row.split(CELL_SEPARATOR);
        for (int i = 0; i < ret.length; i++) {
            ret[i] = handleCommasInData(ret[i]);
        }
        return ret;
    }

    /**
     * If the data contains comma(s), the all data should be surrounded by
     * quotation marks.
     *
     * @param data the data to analyse
     * @return a String surrounded by quotation marks if there was
     * commas in the data. Otherwise, the returned String will be the same
     * as the parameter data.
     */
    private String handleCommasInData(final String data) {
        String newData = data;
        if (data.contains(",")) {
            newData = "\"" + data + "\"";
        }
        return newData;
    }

    /**
     * Gets the number of tables in the Wikipedia page
     * (a table in html is translated in wikitext if
     * it has the attribute "class="wikitable"").
     *
     * @return the number of tables
     */
    private int countWikiTab() {
        Matcher matcher = Pattern.compile(KEY_WORD_WIKITABLE).matcher(this.getTextToParse());
        int occur = 0;
        while (matcher.find()) {
            occur++;
        }
        return occur;
    }

}
