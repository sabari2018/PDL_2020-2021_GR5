package model;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parse wikiText and standardize it with the data structure {@link Table}.
 */
public class ParserWikiText extends Parser {


    private final String HEAD_SEPARATOR = "\\|\\-";
    private final String ROW_SEPARATOR = "\\|\\-";
    private final String CELL_SEPARATOR = "(\\n)*\\| ";
    private String titleOfCurrentPage;
    private ArrayList<String> wikiTextTables;
    private ArrayList<Table> standardizedTables;

    /**
     * @param urlWikiText url to acceed to get the wikitext
     */
    public ParserWikiText(String urlWikiText) {
        this.wikiTextTables = new ArrayList<>();
        this.standardizedTables = new ArrayList<Table>();
        Document doc = this.getPageFromUrl(urlWikiText);
        if (doc != null) {
            this.setTextToParse(doc.html());
            this.titleOfCurrentPage = doc.title();
        }
    }

    /**
     * From a urlWikiText url, access to the page and take the table(s).
     *
     * @return a list of {@link Table}. It's length is the same as the number
     * of tables in the Wikipedia page
     */
    public ArrayList<Table> parseWikiText() {

        // extract all the tables of the Wikipedia page
        // the result put in wikiTextTAbles is still in wikiText
        this.wikiTextTables = extractTablesFromPage();

        //transform the table (format = String) into format = Table
        // and add an object Table to this.standardizedTables for each table
        int numTable = 1;
        for (String table : this.wikiTextTables) {
            Table standardizeTable = new Table(this.titleOfCurrentPage, "wikitext", numTable);
            standardizeTable.getContent().put(0, this.getHead(table));
            String[] rows = this.getRow(table);
            for (int i = 0; i < rows.length; i++) {
                // i+1 because at index 0, we have the head
                standardizeTable.getContent().put(i + 1, this.getCells(rows[i]));
            }
            this.standardizedTables.add(standardizeTable);
            numTable++;
        }
        return this.standardizedTables;
    }

    /**
     * Extract the tables of the page.
     * @return a list which contains the content of the tables
     */
    private ArrayList<String> extractTablesFromPage() {
        ArrayList<String> tablesFromPage = new ArrayList<>();
        int nbWikiTables = this.countWikiTab();
        System.out.println("Nb tableaux : " + nbWikiTables);
        for (int i = 1; i <= nbWikiTables; i++) {
            System.out.println("Un tableau");
            tablesFromPage.add(this.getTable());
        }
        return tablesFromPage;
    }

    /**
     * Extract one table from the page.
     * @return the table
     */
    public String getTable() {
        int startTable = this.getTextToParse().indexOf("{|");
        int endTable = this.getTextToParse().indexOf("|}");
        String oneTable = this.getTextToParse().substring(startTable, endTable + 2);
        this.setTextToParse(this.getTextToParse().substring(endTable + 2));
        return oneTable;
    }

    /**
     *
     * @param wikiTable
     * @return
     */
    public String[] getHead(String wikiTable) {
        String[] separateur = wikiTable.split(HEAD_SEPARATOR);
        String[] tabfinal = new String[separateur.length];
        ArrayList<String> list = new ArrayList<String>();
        if (separateur[0].contains("!")) { /* cas special rencontre */
            for (int i = 0; i < separateur.length; i++) {
                list.add(separateur[i]); //Si le premier split contiens des "!" qui reprÃ©sente une colonne, alors on l'ajoute dans la liste !
            }
        } else {
            for (int i = 1; i < separateur.length; i++) {
                list.add(separateur[i]);
            }
        }
        tabfinal = list.toArray(tabfinal); //Converti la liste (qui contient le tableau entier, spliter sur les |--) en tableau
        String head = tabfinal[0];
        this.setNbColumns(head.split("!").length - 1);
        String[] separateur1 = head.split("!");
        String[] tabfinal1 = new String[this.getNbColumns()];
        for (int i = 0; i < this.getNbColumns(); i++) {
            tabfinal1[i] = separateur1[i + 1]; //on ne prend pas le split[0] car vide
            if (tabfinal1[i].contains("|")) { //Permet de supprimer les balises avant les noms des cols
                String[] separateur2 = tabfinal1[i].split("\\| ");
                tabfinal1[i] = separateur2[1];
            }
        }
        this.setNbColumns(tabfinal1.length);
        return tabfinal1;
    }

    /**
     * Gets a row from a wikitext table.
     * @param table the table (in wikitext)
     * @return a table of String which contains the content of one row
     */
    public String[] getRow(String table) {
        String[] tabFaux = new String[1];
        String[] rows = table.split(ROW_SEPARATOR);
        ArrayList<String> list = new ArrayList<String>(); //on ajoute les sÃ©parateurs dans la liste pour remove ce qu'il y a en trop
        for (int i = 0; i < rows.length; i++) {
            rows[i] = rows[i].replaceAll("align=right", "");
            rows[i] = rows[i].replaceAll("align=left", "");
            list.add(rows[i]);
        }

        if (list.get(0).contains("class=")) {
            list.remove(0);
        }

        if (list.size() == 0) {
            tabFaux[0] = "tableaufaux";
            return tabFaux;
        }

        String[] tableau = new String[list.size()];
        tableau = list.toArray(tableau);

        return tableau;
    }

    /**
     * Gets the cells of the given row.
     * @param row the row
     * @return a table of String which contains the content of the row's cells
     */
    private String[] getCells(String row) {
        String[] cells = row.split(CELL_SEPARATOR);
        System.out.println("NB columns = " + this.getNbColumns() + " vs " + cells.length);
        return cells;
    }

    /**
     * Gets the number of tables in the Wikipedia page.
     * @return the number of tables
     */
    private int countWikiTab() {
        Matcher matcher = Pattern.compile("wikitable").matcher(this.getTextToParse());
        int occur = 0;
        while (matcher.find()) {
            occur++;
        }
        return occur;
    }
}
