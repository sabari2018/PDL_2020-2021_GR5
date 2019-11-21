package model;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses the html page into an "easier" (uniform with wikitext parsing and easier to test) structure for csv generation.
 *
 * @author Emile Georget
 */
public class ParserHTML extends Parser {

    /**
     * The page to parse
     */
    private Document pageHtml;
    private String url;

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
        ArrayList<String> tables = this.getTablesFromPage(parseSourceCodeExamples(pageToParse));
        for (String table : tables) {
            Table parsedTable = new Table(pageHtml.title(), "html", tabNumber);
            ArrayList<String> rows = this.getRowsFromTable(table);

            for (String row : rows) {

                ArrayList<String> cells = this.getCellsFromRow(row);
                String parsedRowCells[] = cells.toArray(new String[cells.size()]);
                //System.out.println(rows.indexOf(row));
                parsedTable.getContent().put(rows.indexOf(row), parsedRowCells);

            }
            parsedTables.add(parsedTable);

            tabNumber++;
        }

        //For debug
        for (Table tbl : parsedTables) {
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
        }

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
        //Gérer le rowspan
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
        //Gérer le colspan
        markedRows = markedRows.replaceAll("<thead[^>]*>|</thead>", "");
        markedRows = markedRows.replaceAll("<tbody[^>]*>|</tbody>", "");
        markedRows = markedRows.replaceAll("<tr[^>]*>|</tr>", "<tr>ROW_TO_EXTRACT");
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

        ArrayList<String> extractedCells = new ArrayList<String>();
        markedCells = markedCells.replaceAll("<th[^>]*>", "<th>CELL_TO_EXTRACT");
        markedCells = markedCells.replaceAll("<td[^>]*>", "<td>CELL_TO_EXTRACT");
        String[] split = markedCells.split("<th>|</th>|<td>|</td>");

        for (int i = 0; i < split.length; i++) {
            if (split[i].contains("CELL_TO_EXTRACT")) {
                split[i] = split[i].replaceAll("CELL_TO_EXTRACT", "");
                split[i] = split[i].replaceAll("ROW_TO_EXTRACT", "");
                split[i] = split[i].replaceAll("<a[^>]*>|</a>", "");
                split[i] = split[i].replaceAll("<i[^>]*>|</i>", " ");
                split[i] = split[i].replaceAll("&nbsp;", " ");
                split[i] = split[i].replaceAll("<br>|<br/>", " ");
                split[i] = split[i].replaceAll("<[^>]*>|</[^>]*>", "");
                split[i] = split[i].replaceAll("&lt;", "<");
                split[i] = split[i].replaceAll("&gt;", ">");
                extractedCells.add(escapeComasAndQuotes(split[i]));
            }
        }
        return extractedCells;
    }

    /**
     * Prepares source code examples contained in page to avoid conflicts with the parser
     *
     * @param pageToParse the page to parse
     * @return the page with compatible source code examples
     */
    public String parseSourceCodeExamples(String pageToParse) {
        String compatiblePage = pageToParse;
        Pattern p = Pattern.compile("<code[^>]*>([^\\\"]+)*?<\\/code>");
        Matcher m = p.matcher(compatiblePage);
        compatiblePage.replaceAll("<code[^>]*>|</code>", "");
        if (m.matches()) {
            m.group().replaceAll("((?:^|)<(?:$|))", "&lt;");
            m.group().replaceAll("((?:^|)>(?:$|))", "&gt;");
        }
        p = Pattern.compile("<pre[^>]*>([^\\\"]+)*?<\\/pre>");
        m = p.matcher(compatiblePage);
        compatiblePage.replaceAll("<pre[^>]*>|</pre>", "");
        if (m.matches()) {
            m.group().replaceAll("((?:^|)<(?:$|))", "&lt;");
            m.group().replaceAll("((?:^|)>(?:$|))", "&gt;");
        }

        return compatiblePage;
    }


    /**
     * Adds "" to each data containing commas or quotation marks to the data.
     * This is done to avoid formatting problems in csv, because commas are considered as the columns separators.
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
