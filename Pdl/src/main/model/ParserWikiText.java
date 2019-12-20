package model;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author hheinle
 * <p>
 * Parse wikiText and standardize it with the data structure {@link Table}.
 */
public class ParserWikiText extends Parser {
    private static final Logger LOGGER = Logger.getLogger(ParserWikiText.class);
    private static final String KEY_WORD_WIKITABLE = "wikitable";
    private static final String START_WIKITABLE = ".*class=.*wikitable.*(\\n*(\\|-.*|!|\\|+.*)*)*";
    private static final String END_WIKITABLE = "\\|}";
    private static final String ROW_SEPARATOR = "\\n(\\|-.*\\n*)*(\\n\\|?-?)";
    private static final String CELL_SEPARATOR = "\\n\\s*\\||\\n\\s*!|!{2}|\\|{2}";
    private static final String REGEX_COLSPAN = ".*colspan=\"?(\\d*)\\s?\"?.*";
    private static final String REGEX_ROWSPAN = ".*rowspan=\"?(\\d*)\\s?\"?.*";
    private static final String REGEX_COMMENT = "(?=&lt;!--)([\\s\\S]*?)--&gt;";
    private String titleOfCurrentPage;
    private ArrayList<String> wikiTextTables;
    private ArrayList<Table> standardizedTables;
    private HashMap<Integer, Integer> rangesOfRowspan;

    /**
     * Empty Constructor.
     */
    public ParserWikiText() {
        String log4jConfPath = "Pdl/resources/log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
        this.wikiTextTables = new ArrayList<String>();
        rangesOfRowspan = new HashMap<Integer, Integer>();
    }

    /**
     * Set the url of the page we want to parse.
     * In the same time, it set the attribute textToParse
     *
     * @param urlWikiText new url to access
     */
    public void setUrlWikiText(final String urlWikiText) {
        this.standardizedTables = new ArrayList<Table>();
        Document doc = this.getPageFromUrl(urlWikiText);
        if (doc != null) {
            this.setTextToParse(doc.html().replaceAll(REGEX_COMMENT, ""));
            this.titleOfCurrentPage = doc.title();
        }
        String[] splitUrl = urlWikiText.split("title=");
        String[] splitTitle = splitUrl[splitUrl.length - 1].split("&action=");
        this.titleOfCurrentPage = splitTitle[0];
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
            HashMap<Integer, String[]> contentOfTable = new HashMap<Integer, String[]>();
            ArrayList<String> rows = this.getRowsFromTable(table);
            for (int i = 0; i < rows.size(); i++) {
                contentOfTable.put(i, this.getCellsFromRow(rows.get(i)));
            }
            standardizeTable.setContent(contentOfTable);
            this.standardizedTables.add(standardizeTable);
            numTable++;
        }
        return this.standardizedTables;
    }

    /**
     * Extract the tables from the page.
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
            startTable = m.end();
            this.setTextToParse(this.getTextToParse().substring(startTable));
            startTable = 0;
        }
        Pattern p2 = Pattern.compile(END_WIKITABLE);
        Matcher m2 = p2.matcher(this.getTextToParse());
        if (m2.find()) {
            endTable = m2.start();
            if (endTable < startTable) {
                // sometimes, a table finish by "|} |}". So the following table
                // end before it start
                LOGGER.debug("The end of the table is before the start");
                m2.find();
                endTable = m2.start();
            }
        }
        oneTable = this.getTextToParse().substring(startTable, endTable);
        this.setTextToParse(this.getTextToParse().substring(endTable + 2));
        return oneTable;
    }

    /**
     * Gets a row from a table.
     *
     * @param table the table (in wikitext)
     * @return a list of String. One element corresponds to
     * the content of one row.
     */
    private ArrayList<String> getRowsFromTable(final String table) {
        String[] rows = table.split(ROW_SEPARATOR);
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < rows.length; i++) {
            // remove unwanted String
            rows[i] = rows[i].replaceAll("&amp;nbsp;", " ");
            rows[i] = rows[i].replaceAll("&lt;", "<");
            rows[i] = rows[i].replaceAll("\n", "ENTER");
            rows[i] = rows[i].replaceAll("&gt;", ">");
            rows[i] = rows[i].replaceAll("<ref[^>]*>(.*?)</ref>", "");
            rows[i] = rows[i].replaceAll("&amp", " ");
            rows[i] = rows[i].replaceAll("Color[^>]*darkgray", " ");
            rows[i] = rows[i].replaceAll("'*", "");
            rows[i] = rows[i].replaceAll("ENTER", "\n");
            if (!rows[i].trim().isEmpty()) {
                list.add(rows[i]);
            }
        }
        return list;
    }

    /**
     * Gets the cells of the given row.
     *
     * @param row the row
     * @return a table of String. One case of this table corresponds
     * to the content of the row's cells.
     */
    private String[] getCellsFromRow(final String row) {
        String[] cells = row.split(CELL_SEPARATOR, -1);
        ArrayList<String> list = handleCells(cells);
        return list.toArray(new String[0]);
    }

    /**
     * Runs through the row's cells to delete the characters we don't want.
     * Handle rowspan and colspan.
     *
     * @param cells cells of one row
     * @return list of the cells without the unwanted characters
     */
    private ArrayList<String> handleCells(final String[] cells) {
        ArrayList<String> cellsList = new ArrayList(Arrays.asList(cells));
        int nbCells = cells.length;
        int nbCellsWithRowspan = cells.length + rangesOfRowspan.size();

        for (int i = 0; i < nbCellsWithRowspan; i++) {
            Matcher matcherColSpan = null;
            Matcher matcherRowSpan = null;
            if (i < cellsList.size()) {
                matcherColSpan = Pattern.compile(REGEX_COLSPAN).matcher(cellsList.get(i));
                matcherRowSpan = Pattern.compile(REGEX_ROWSPAN).matcher(cellsList.get(i));
            }
            // handle colspan
            if (i < cellsList.size()) {
                if (matcherColSpan != null && matcherColSpan.matches()) {
                    cellsList.set(i, cellsList.get(i).replaceAll(".*colspan=\"?(\\d*)\\s?\"?", " "));
                    int colspan = Integer.parseInt(matcherColSpan.group(1));
                    if (colspan != 0) {
                        for (int j = 0; j < colspan - 1; j++) {
                            cellsList.add(i + 1, "");
                            i++;
                            nbCellsWithRowspan++;
                        }
                    }
                }
            }
            // handle rowspan
            if (rangesOfRowspan.get(i) != null && rangesOfRowspan.get(i) > 0) {
                if (i < cellsList.size()) {
                    cellsList.add(i, "");
                } else {
                    cellsList.add("");
                }
                rangesOfRowspan.put(i, rangesOfRowspan.get(i) - 1);
                if (rangesOfRowspan.get(i) == 0) {
                    rangesOfRowspan.remove(i);
                }
            }

            if (matcherRowSpan != null && matcherRowSpan.matches()) {
                cellsList.set(i, cellsList.get(i).replaceAll(".*rowspan=\"?(\\d*)\\s?\"?", ""));
                rangesOfRowspan.put(i, Integer.parseInt(matcherRowSpan.group(1)) - 1);
            }
        }
        for (int i = 0; i < nbCells; i++) {
            if (!cellsList.get(i).trim().isEmpty()) {
                cellsList.set(i, cellsList.get(i).replaceAll("\n", " "));
                cellsList.set(i, cellsList.get(i).replaceAll(START_WIKITABLE, ""));
                cellsList.set(i, cellsList.get(i).replaceAll("&lt;", ""));
                cellsList.set(i, cellsList.get(i).replaceAll("&gt;", ""));
                cellsList.set(i, cellsList.get(i).replaceAll("&lt;[^&gt;]*&gt;.*&lt;/[^&gt;]*&gt;", ""));
                cellsList.set(i, cellsList.get(i).replaceAll("<[^>]*>|</[^>]*>", ""));
                cellsList.set(i, cellsList.get(i).replaceAll("\\[\\[.*?\\|", ""));
                cellsList.set(i, cellsList.get(i).replaceAll("\\[\\[", ""));
                cellsList.set(i, cellsList.get(i).replaceAll("\\{\\{.*?\\|", ""));
                cellsList.set(i, cellsList.get(i).replaceAll("\\{\\{", ""));
                cellsList.set(i, cellsList.get(i).replaceAll("\\|", ""));
                cellsList.set(i, cellsList.get(i).replaceAll("style=\".*\"", ""));
                cellsList.set(i, cellsList.get(i).replaceAll("align=\".*\"", ""));
                cellsList.set(i, cellsList.get(i).replaceAll("}}", ""));
                cellsList.set(i, cellsList.get(i).replaceAll("]]", ""));
                cellsList.set(i, cellsList.get(i).replaceAll("<source.*>", ""));
                cellsList.set(i, cellsList.get(i).replaceAll("\n", "  "));
                cellsList.set(i, cellsList.get(i).replaceAll("^\\s*!", ""));
                cellsList.set(i, cellsList.get(i).replaceAll("data-sort-type=\"[^\"]*\"", ""));
                cellsList.set(i, handleCommasQuotesInData(cellsList.get(i)));
            }
        }
        return cellsList;
    }

    /**
     * If the data contains comma(s) are quotes, the data should be surrounded by
     * quotation marks.
     *
     * @param data the data to analyse
     * @return a String surrounded by quotation marks if there was
     * commas or quotes in the data. Otherwise, the returned String will be the same
     * as the parameter data.
     */
    private String handleCommasQuotesInData(final String data) {
        String newData = data;
        if (newData.contains("\"")) {
            newData = newData.replaceAll("\"", "\"\"");
        }
        if (newData.contains(",") || newData.contains("\"")) {
            newData = "\"" + newData + "\"";
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
