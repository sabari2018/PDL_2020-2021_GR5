package model;

import org.jsoup.nodes.Document;

import java.util.ArrayList;

/**
 *
 */
public class ParserHTML extends  Parser {

    public ParserHTML() {

    }

    public ParserHTML(String url) {
        Document pageHtml = this.getPageFromUrl(url);
        setTextToParse(pageHtml.html());
        parseHtml(getTextToParse());
    }

    /**
     * Parses an HTML page to get the tables from the page.
     * @return a list of {@link Table}
     */
    public ArrayList<Table> parseHtml(String textToParse) {

        ArrayList<String> tables=this.getTablesFromPage(textToParse);
        for(String s : tables) {
            ArrayList<String> rows=this.getRowsFromTable(s);

            Table table = new Table();

            //table.getContent().put()
        }
        ArrayList<Table>parsedTables=new ArrayList<Table>();

        return parsedTables;
    }

    public ArrayList<String> getTablesFromPage(String textToParse) {
        String markedTables=textToParse;
        ArrayList<String>extractedTables=new ArrayList<String>();
        //Gérer le rowspan
        markedTables=markedTables.replaceAll("<table.*?class=\"wikitable[^\"]*\"[^>]*>", "<table>TABLE_TO_EXTRACT");
        String [] split = markedTables.split("<table>|</table>");
        for(int i=0; i<split.length;i++) {
            if(split[i].contains("TABLE_TO_EXTRACT")){
                extractedTables.add(split[i]);
                System.out.println(extractedTables.size());
            }
        }

        for(String s : extractedTables) {
            //System.out.println(s);
        }

        return extractedTables;
    }

    public ArrayList<String> getRowsFromTable(String table) {
        String markedRows=table;
        ArrayList<String>extractedRows=new ArrayList<String>();
        //Gérer le colspan
        markedRows=markedRows.replaceAll("<thead[^>]*>|</thead>", "");
        markedRows=markedRows.replaceAll("<tbody[^>]*>|</tbody>", "");
        markedRows=markedRows.replaceAll("<tr[^>]*>", "<tr>ROW_TO_EXTRACT");
        String [] split = markedRows.split("<tr>|</tr>");
        for(int i=0; i<split.length;i++) {

            if(split[i].contains("ROW_TO_EXTRACT")) {
                split[i]=split[i].replaceAll("ROW_TO_EXTRACT", "");
                split[i]=split[i].replaceAll("<a[^>]*>|</a>", "");
                split[i]=split[i].replaceAll("<i[^>]*>|</i>", " ");
                split[i]=split[i].replaceAll("&nbsp;", "");
                split[i]=split[i].replaceAll("<br>|<br/>", " ");
                split[i]=split[i].replaceAll("<b>|</b>", " ");
                split[i]=split[i].replaceAll("&lt", "<");
                split[i]=split[i].replaceAll("&gt", ">");

                extractedRows.add(split[i]);
            }
            for(String s : extractedRows) {
                System.out.println(s);
            }

        }




        return extractedRows;
    }

    public String cleanHtml (String row) {
        return null;
    }

    public String escapeComasAndQuotes(String row) {
        return null;
    }

}
