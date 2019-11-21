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

    private static final String START_WIKITABLE = "\\{\\|.*class=\"*.*wikitable.*\"*";
    private static final String END_WIKITABLE = "\\|}";
    private static final String KEY_WORD_WIKITABLE = "wikitable";
    private final Logger logger = Logger.getLogger(ParserWikiText.class);
    private final String HEAD_CELL_SEPARATOR = "(\\n)\\!* ";
    private final String ROW_SEPARATOR = "\\|\\-(\\n)(\\|)?";
    private final String CELL_SEPARATOR = "(\\n)\\| ";

    private final String REGEX_COLSPAN = ".*colspan=\"?(\\d*)\\s?\"?.*";
//    private final String REGEX_COLSPAN = ".*colspan=\"?(\\d*)\\s?\"?";
//    private final String REGEX_COLSPAN = ".*colspan=\"?(\\d*)\\s?\"?.*\\|";

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
            HashMap<Integer, String[]> contentOfTable = new HashMap<>();
            contentOfTable.put(0, this.getTableHead(table));
            ArrayList<String> rows = this.getTableRow(table);
            for (int i = 0; i < rows.size(); i++) {
                contentOfTable.put(i + 1, this.getCells(rows.get(i)));
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
        String[] rows = table.split(ROW_SEPARATOR);
        String headRow = rows[0];
        String[] headCells = headRow.split(HEAD_CELL_SEPARATOR);
        ArrayList<String> list = handleCells(headCells);
        return list.toArray(new String[0]);
    }



    /**
     * Gets a row from a table.
     *
     * @param table the table (in wikitext)
     * @return a table of String. One case corresponds to
     * the content of one row.
     */
    private ArrayList<String> getTableRow(final String table) {
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
            rows[i] = rows[i].replaceAll("\\{\\{", "");
            rows[i] = rows[i].replaceAll("}}", "");


            if (!rows[i].trim().isEmpty()) {
                list.add(rows[i]);
            }
        }
//        String[] tableau = new String[list.size()];
//        tableau = list.toArray(tableau);
        return list;
    }

    /**
     * Gets the cells of the given row.
     *
     * @param row the row
     * @return a table of String. One case of this table corresponds
     * to the content of the row's cells.
     */
//    private String[] getCells(final String row) {
//        String[] cells = row.split(CELL_SEPARATOR);
//        int nbCells = cells.length;
//        for (int i = 0; i < nbCells; i++) {
//            if (!cells[i].trim().isEmpty()) {
//                cells[i] = cells[i].replaceAll("\n", " ");
//                cells[i] = handleCommasInData(cells[i]);
//                Matcher matcher = Pattern.compile(REGEX_COLSPAN).matcher(cells[i]);
//                if (matcher.matches()) {
//                    cells[i] = cells[i].replaceAll(REGEX_COLSPAN, "");
//                    int colspan = Integer.parseInt(matcher.group(1));
//                    nbCells += colspan;
////                this.setNbColumns(this.getNbColumns() + colspan);
//                    shift(cells, colspan);
////                    for (int j = 1; j <= colspan; j++) {
////                        cells[i] += cells[i];
////                        cells[i + 1] += cells[i];
//////                        i++;
////                    }
//                } else {
//                    nbCells--;
//                    i--;
//                }
//            }
//        }
//        return cells;
//    }

    /**
     * Gets the cells of the given row.
     *
     * @param row the row
     * @return a table of String. One case of this table corresponds
     * to the content of the row's cells.
     */
    private String[] getCells(final String row) {
        String[] cells = row.split(CELL_SEPARATOR);
        ArrayList<String> list = handleCells(cells);
        return list.toArray(new String[0]);
    }

    private ArrayList<String> handleCells(String[] headCells) {
        ArrayList<String> list = new ArrayList(Arrays.asList(headCells));
        for (int i = 1; i < headCells.length; i++) {
            if (!list.get(i).trim().isEmpty()) {
                list.set(i, list.get(i).replaceAll("\n", " "));
                list.set(i, handleCommasInData(list.get(i)));
                list.set(i, list.get(i).replaceAll(START_WIKITABLE, ""));
                list.set(i, list.get(i).replaceAll("style=\".*\"", ""));
                Matcher matcher = Pattern.compile(REGEX_COLSPAN).matcher(list.get(i));
                if (matcher.matches()) {
                    list.set(i, list.get(i).replaceAll(".*colspan=\"?(\\d*)\\s?\"?", ""));
//                    list.set(i, currentCell);
                    int colspan = Integer.parseInt(matcher.group(1));
                    for (int j = 0; j < colspan - 1; j++) {
                        list.add(i + 1, list.get(i));
                    }
                }
            }
        }
        return list;
    }

    public String[] shift(String[] tab, int k) {
        int n = tab.length;
        String[] ret = new String[n + k];

        for (int j = 0; j < k; j++) {
            for (int i = n - 1; i > 0; i--) {
                String x = tab[i];
                ret[i] = tab[i - 1];
                ret[i - 1] = x;
            }
        }

        for (int p = 0; p < ret.length; p++) {
            System.out.println("À l'emplacement " + p + " du tableau nous avons = " + ret[p]);
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
