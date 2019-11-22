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

    private static final Logger logger = Logger.getLogger(ParserWikiText.class);

    private static final String KEY_WORD_WIKITABLE = "wikitable";
    private static final String START_WIKITABLE = ".*class=.*wikitable.*\\n*(\\|-|!)(\\n!)?";
    private static final String END_WIKITABLE = "\\|}";
    private static final String ROW_SEPARATOR = "\\|\\-\\s*\\n\\|?";
    private static final String CELL_SEPARATOR = "\\n\\|[^-]|\\n!*|!{2}";
    private static final String REGEX_COLSPAN = ".*colspan=\"?(\\d*)\\s?\"?.*";
    private static final String REGEX_ROWSPAN = ".*rowspan=\"?(\\d*)\\s?\"?.*";
    private String urlWikiText;
    private String titleOfCurrentPage;
    private ArrayList<String> wikiTextTables;
    private ArrayList<Table> standardizedTables;
    private int rangeOfRowspan;

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
            HashMap<Integer, String[]> contentOfTable = new HashMap<Integer, String[]>();
            ArrayList<String> rows = this.getTableRow(table);
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
                logger.debug("The end of the table is before the start");
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
    private ArrayList<String> getTableRow(final String table) {
        String[] rows = table.split(ROW_SEPARATOR);
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < rows.length; i++) {
            // remove unwanted String
            rows[i] = rows[i].replaceAll("align=right", "");
            rows[i] = rows[i].replaceAll("align=left", "");
            rows[i] = rows[i].replaceAll("ref&gt;[^>]*/ref&gt;", "");
            rows[i] = rows[i].replaceAll("&lt;ref[^>]*/ref&gt;", "");
            rows[i] = rows[i].replaceAll("align=\"left\"", "");
            rows[i] = rows[i].replaceAll("&lt;br\\s?/?&gt;", " ");
            rows[i] = rows[i].replaceAll("&lt;ref&gt;", " ");
            rows[i] = rows[i].replaceAll("br /&gt;", " ");
            rows[i] = rows[i].replaceAll("&amp;nbsp;", " ");
            rows[i] = rows[i].replaceAll("&amp", " ");
            rows[i] = rows[i].replaceAll("Color[^>]*darkgray", " ");
            rows[i] = rows[i].replaceAll("&lt;", "");
            rows[i] = rows[i].replaceAll("&gt;", "");
            rows[i] = rows[i].replaceAll("'*", "");


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
        String[] cells = row.split(CELL_SEPARATOR);
        ArrayList<String> list = handleCells(cells);
        return list.toArray(new String[0]);
    }

    /**
     * @param cells cells of one row
     * @return
     */
    private ArrayList<String> handleCells(String[] cells) {
        ArrayList<String> list = new ArrayList(Arrays.asList(cells));
        for (int i = 0; i < cells.length; i++) {
            if (!list.get(i).trim().isEmpty()) {
                list.set(i, list.get(i).replaceAll("\n", " "));
                list.set(i, handleCommasInData(list.get(i)));
                list.set(i, list.get(i).replaceAll(START_WIKITABLE, ""));
                list.set(i, list.get(i).replaceAll("\\|", ""));
                list.set(i, list.get(i).replaceAll("style=\".*\"", ""));
                list.set(i, list.get(i).replaceAll("\\{\\{", ""));
                list.set(i, list.get(i).replaceAll("\\[\\[", ""));
                list.set(i, list.get(i).replaceAll("}}", ""));
                list.set(i, list.get(i).replaceAll("]]", ""));
                list.set(i, list.get(i).replaceAll("<source.*>", ""));
                list.set(i, list.get(i).replaceAll("\n", "  "));
                list.set(i, list.get(i).replaceAll("^! ", ""));
                Matcher matcherColspan = Pattern.compile(REGEX_COLSPAN).matcher(list.get(i));
                Matcher matcherRowspan = Pattern.compile(REGEX_ROWSPAN).matcher(list.get(i));
                if (matcherColspan.matches()) {
                    list.set(i, list.get(i).replaceAll(".*colspan=\"?(\\d*)\\s?\"?", " "));
                    int colspan = Integer.parseInt(matcherColspan.group(1));
                    for (int j = 0; j < colspan - 1; j++) {
                        list.add(i + 1, list.get(i));
                    }
                }
//                if (matcherRowspan.matches()) {
//                    list.set(i, list.get(i).replaceAll(".*rowspan=\"?(\\d*)\\s?\"?", ""));
//                    int colspan = Integer.parseInt(matcherColspan.group(1));
//                    for (int j = 0; j < colspan - 1; j++) {
//                        list.add(i + 1, list.get(i));
//                    }
//                }
            }
        }
//        list.removeAll(Collections.singletonList(""));
        return list;
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
