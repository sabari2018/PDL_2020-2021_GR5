package model;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Parses the html page into an "easier" (uniform with wikitext parsing and easier to test) structure for csv generation.
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
        ArrayList<String> tables = this.getTablesFromPage(pageToParse);
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
        markedTables = markedTables.replaceAll("(?!<code[^>]*?>|<pre[^>]*?>)(<table.*?class=\".*?\\swikitable[^\"]*\"[^>]*>)(?![^<]*?</code>|[^<]*?</pre>)", "<table>TABLE_TO_EXTRACT");
        String[] split = markedTables.split("(?!<code[^>]*?>|<pre[^>]*?>)(<table>|</table>)(?![^<]*?</code>|[^<]*?</pre>)");
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
        markedRows = markedRows.replaceAll("(?!<code[^>]*?>|<pre[^>]*?>)(<thead[^>]*>|</thead>)(?![^<]*?</code>|[^<]*?</pre>)", "");
        markedRows = markedRows.replaceAll("(?!<code[^>]*?>|<pre[^>]*?>)(<tbody[^>]*>|</tbody>)(?![^<]*?</code>|[^<]*?</pre>)", "");
        markedRows = markedRows.replaceAll("(?!<code[^>]*?>|<pre[^>]*?>)(<tr[^>]*>|</tr>)(?![^<]*?</code>|[^<]*?</pre>)", "<tr>ROW_TO_EXTRACT");
        String[] split = markedRows.split("(?!<code[^>]*?>|<pre[^>]*?>)(<tr>|</tr>)(?![^<]*?</code>|[^<]*?</pre>)");
        for (int i = 0; i < split.length; i++) {

            if (split[i].contains("ROW_TO_EXTRACT")) {
                split[i] = split[i].replaceAll("(?!<code[^>]*?>|<pre[^>]*?>)(ROW_TO_EXTRACT)(?![^<]*?</code>|[^<]*?</pre>)", "");
                split[i] = split[i].replaceAll("(?!<code[^>]*?>|<pre[^>]*?>)(<a[^>]*>|</a>)(?![^<]*?</code>|[^<]*?</pre>)", "");
                split[i] = split[i].replaceAll("(?!<code[^>]*?>|<pre[^>]*?>)(<i[^>]*>|</i>)(?![^<]*?</code>|[^<]*?</pre>)", " ");
                split[i] = split[i].replaceAll("(?!<code[^>]*?>|<pre[^>]*?>)(&nbsp;)(?![^<]*?</code>|[^<]*?</pre>)", " ");
                split[i] = split[i].replaceAll("(?!<code[^>]*?>|<pre[^>]*?>)(<br>|<br/>)(?![^<]*?</code>|[^<]*?</pre>)", " ");
                split[i] = split[i].replaceAll("(?!<code[^>]*?>|<pre[^>]*?>)(<[^>]*>|</[^>]*>)(?![^<]*?</code>|[^<]*?</pre>)", "");


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
        markedCells = markedCells.replaceAll("(?!<code[^>]*?>|<pre[^>]*?>)(<th[^>]*>)(?![^<]*?</code>|[^<]*?</pre>)", "<th>CELL_TO_EXTRACT");
        markedCells = markedCells.replaceAll("(?!<code[^>]*?>|<pre[^>]*?>)(<td[^>]*>)(?![^<]*?</code>|[^<]*?</pre>)", "<td>CELL_TO_EXTRACT");
        String[] split = markedCells.split("(?!<code[^>]*?>|<pre[^>]*?>)(<th>|</th>|<td>|</td>)(?![^<]*?</code>|[^<]*?</pre>)");
        for (int i = 0; i < split.length; i++) {

            if (split[i].contains("CELL_TO_EXTRACT")) {
                split[i] = split[i].replaceAll("(?!<code[^>]*?>|<pre[^>]*?>)(CELL_TO_EXTRACT)(?![^<]*?</code>|[^<]*?</pre>)", "");
                split[i] = split[i].replaceAll("(?!<code[^>]*?>|<pre[^>]*?>)(&lt)(?![^<]*?</code>|[^<]*?</pre>)", "<");
                split[i] = split[i].replaceAll("(?!<code[^>]*?>|<pre[^>]*?>)(&gt)(?![^<]*?</code>|[^<]*?</pre>)", ">");
            }

            extractedCells.add(escapeComasAndQuotes(split[i]));
        }
        return extractedCells;
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

        if(data.contains("\"")) {
            escapedData.replaceAll("\"","\"\"");
        }
        if(data.contains(",") || data.contains("\"")) {
            escapedData="\""+ escapedData +"\"";
        }
        return escapedData;
    }


    /**
     * Sets the page url.
     *
     * @param url the url to set
     */
    public void setUrlHtml(String url) {

        this.url=url;

    }


    /**
     * Gets html page in a String format.
     * @return the html page
     */
    public String getHtmlPage() {
        String pageToParse="";

        pageHtml = this.getPageFromUrl(url);
        setTextToParse(pageHtml.html());
        pageToParse=getTextToParse();

        return pageToParse;
    }

}
