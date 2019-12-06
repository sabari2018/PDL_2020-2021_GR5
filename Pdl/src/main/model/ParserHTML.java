package model;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses the html page into an "easier" (uniform with wikitext parsing and easier to test) structure for csv generation.
 *
 * @author Emile Georget
 */
public class ParserHTML extends Parser {

    private final String REGEX_COLSPAN = ".*colspan=\"?(\\d*)\\s?\"?.*";
    private final String REGEX_ROWSPAN = ".*rowspan=\"?(\\d*)\\s?\"?.*";
    /**
     * The page to parse
     */
    private Document pageHtml;
    private String url;
    private HashMap<Integer, Integer> rowSpanMap = new HashMap<>();

    /**
     * Parses an HTML page to generate an list of parsed tables (represented by {@link Table} object).
     * A parsed table is represented by a Hashmap with an Integer key (the csv line number) and
     * a table of String which contains the values for this row.
     *
     * @return a list of {@link Table}
     */
    public ArrayList<Table> parseHtml() {

        String pageToParse = getHtmlPage();

        int tabNumber = 0;
        ArrayList<Table> parsedTables = new ArrayList<Table>();
        ArrayList<String> tables = this.getTablesFromPage(pageToParse);
        for (String table : tables) {
            Table parsedTable = new Table(pageHtml.title(), "html", tabNumber);
            ArrayList<String> rows = this.getRowsFromTable(table);
            for (String row : rows) {

                ArrayList<String> cells = this.getCellsFromRow(row);
                String[] parsedRowCells = cells.toArray(new String[cells.size()]);
                parsedTable.getContent().put(rows.indexOf(row), parsedRowCells);

            }
            parsedTables.add(parsedTable);

            tabNumber++;
        }

        //For debug
        /*for (Table tbl : parsedTables) {
            System.out.println("=========TABLE===========");
            HashMap<Integer, String[]> tbl2 = tbl.getContent();
            for (Integer i : tbl2.keySet()) {
                System.out.println("*****ROW*****");
                for (String str : tbl2.get(i)) {
                    System.out.println(str);
                }
                System.out.println("*************");
            }
            System.out.println("=========================");
        }*/

        return parsedTables;
    }


    /**
     * Extracts the html tables from the page. A table is marked with "<table></table>" tags.
     * Only the tables of class "wikitable" are extracted. These are the tables containing relevant data.
     *
     * @param pageToParse the page to parse
     * @return a list of tables (the tables contained in the page)
     */
    public ArrayList<String> getTablesFromPage(String pageToParse) {
        String markedTables = pageToParse;
        ArrayList<String> extractedTables = new ArrayList<String>();
        markedTables = markedTables.replaceAll("<table(?=[^>]*class=\".*\\bwikitable\\b.*\")[^>]*>", "<table>TABLE_TO_EXTRACT");
        String[] split = markedTables.split("<table>|</table>");
        for (int i = 0; i < split.length; i++) {
            if (split[i].contains("TABLE_TO_EXTRACT")) {
                extractedTables.add(split[i]);
            }
        }

        return extractedTables;
    }


    /**
     * Extracts the html rows from a table. A row is marked with "<tr></tr>" tags.
     *
     * @param table the table with the rows to extract
     * @return a list of rows (the rows contained in the table)
     */
    public ArrayList<String> getRowsFromTable(String table) {
        String markedRows = table;
        ArrayList<String> extractedRows = new ArrayList<String>();
        markedRows = markedRows.replaceAll("<thead[^>]*>|</thead>", "");
        markedRows = markedRows.replaceAll("<tbody[^>]*>|</tbody>", "");
        markedRows = markedRows.replaceAll("<tr[^>]*>", "<tr>ROW_TO_EXTRACT");
        String[] split = markedRows.split("<tr>|</tr>");
        for (int i = 0; i < split.length; i++) {

            if (split[i].contains("ROW_TO_EXTRACT")) {
                extractedRows.add(split[i]);
            }

        }
        return extractedRows;
    }


    /**
     * Extracts the html cells from a table. A cell is normally marked with "<tr></tr>" tags.
     * It can also be marked with "<th></th>" tags if the cells are contained in the header.
     *
     * @param row the row with the cells to extract
     * @return a list of data (the data contained in the row cells)
     */
    public ArrayList<String> getCellsFromRow(String row) {
        String markedCells = row;
        markedCells = markedCells.replaceAll("ROW_TO_EXTRACT", "");

        markedCells = markedCells.replaceAll("</th>", "</th>CELL_TO_EXTRACT");
        markedCells = markedCells.replaceAll("</td>", "</td>CELL_TO_EXTRACT");
        markedCells = markedCells.replaceAll("^ +", "");
        markedCells = markedCells.replaceAll("\n", "").replace("\r", "");
        markedCells = markedCells.replaceAll(" +", " ");
        String[] split = markedCells.split("CELL_TO_EXTRACT");

        return handleCells(split);
    }

    /**
     * Parses colspan and rowspan to comply with CSV format.
     * We replace colspan and rowspan by empty cells.
     * For example : if we have a colspan of 2, we will add 1 empty cell next to the cell containing the colspan attribute.
     * Another example : if we have a rowspan of 3, we will add 1 empty cell just below the cell containing the rowspan and 1 empty cell below the first empty cell.
     * @param cells table containing the cells from a row
     * @return an {@link ArrayList} containing the cells from a row after the eventual addition of empty cells.
     */
    private ArrayList<String> handleCells(String[] cells) {
        ArrayList<String> cellsList = new ArrayList(Arrays.asList(cells));
        cellsList.remove(cellsList.get(cellsList.size() - 1));
        int nbCells = cellsList.size();
        for (int i = 0; i < nbCells; i++) {
            Matcher matcherColSpan = Pattern.compile(REGEX_COLSPAN).matcher(cellsList.get(i));
            Matcher matcherRowSpan = Pattern.compile(REGEX_ROWSPAN).matcher(cellsList.get(i));
            if (matcherColSpan.matches()) {
                int colspan = Integer.parseInt(matcherColSpan.group(1));
                if (colspan != 0) {
                    for (int j = 0; j < colspan - 1; j++) {
                        cellsList.add(i + 1, "");
                        nbCells++;
                    }
                }
            }
            if (rowSpanMap.get(i) != null && rowSpanMap.get(i) > 0) {
                nbCells++;
                cellsList.add(i, "");
                rowSpanMap.put(i, rowSpanMap.get(i) - 1);
            }
            if (matcherRowSpan.matches()) {
                rowSpanMap.put(i, Integer.parseInt(matcherRowSpan.group(1)) - 1);
            }
        }

        //If a rowspan is at the last position of the row, it cannot be treated in the loop (because its id == nbCells), so it is treated afterwards
        if (rowSpanMap.get(nbCells) != null && rowSpanMap.get(nbCells) != 0) {
            cellsList.add(nbCells, "rowspan");
            rowSpanMap.put(nbCells, rowSpanMap.get(nbCells) - 1);
            nbCells++;
        }


        for (int i = 0; i < nbCells; i++) {
            cellsList.set(i, cellsList.get(i).replaceAll("<a[^>]*>|</a>", ""));
            cellsList.set(i, cellsList.get(i).replaceAll("<i[^>]*>|</i>", " "));
            cellsList.set(i, cellsList.get(i).replaceAll("&nbsp;", " "));
            cellsList.set(i, cellsList.get(i).replaceAll("<br>|<br/>", " "));
            cellsList.set(i, cellsList.get(i).replaceAll("<[^>]*>|</[^>]*>", ""));
            cellsList.set(i, cellsList.get(i).replaceAll("&lt;", "<"));
            cellsList.set(i, cellsList.get(i).replaceAll("&gt;", ">"));
            cellsList.set(i, escapeComasAndQuotes(cellsList.get(i)));
        }

        return cellsList;
    }

    /**
     * Adds "" to each data containing commas or quotation marks to the data.
     * This is done to avoid formatting problems in CSV, because commas are considered as column separators.
     *
     * @param data the data to check
     * @return the reformatted data
     */
    public String escapeComasAndQuotes(String data) {
        String escapedData = data;
        if (escapedData.contains("\"")) {
            escapedData = escapedData.replaceAll("\"", "\"\"");
        }
        if (escapedData.contains(",") || escapedData.contains("\"")) {
            escapedData = "\"" + escapedData + "\"";
        }
        return escapedData;
    }


    /**
     * Sets the page url.
     *
     * @param url the url to set
     */
    public void setUrlHtml(String url) {

        this.url = url;

    }


    /**
     * Gets html page in a String format.
     *
     * @return the html page
     */
    public String getHtmlPage() {
        String pageToParse = "";

        pageHtml = this.getPageFromUrl(url);
        setTextToParse(pageHtml.html());
        pageToParse = getTextToParse();

        return pageToParse;
    }

}

