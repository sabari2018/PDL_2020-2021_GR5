package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProcessWikiUrl {

    private List<WikiUrl> listWikiUrl;
    private List<Table> listTable;
    private ParserHTML parserHTML;
    private ParserWikiText parserWikiText;
    private Converter converter;

    public ProcessWikiUrl(){
        listWikiUrl = new ArrayList<WikiUrl>();
        listTable = new ArrayList<Table>();
        parserHTML = new ParserHTML();
        parserWikiText = new ParserWikiText();
        converter = new Converter();
    }

    /**
     *
     * @return list of all WikiUrl
     */
    public List<WikiUrl> getListWikiUrl() {
        return listWikiUrl;
    }

    /**
     *
     * @return list of all Table parsed
     */
    public List<Table> getListTable() {
        return listTable;
    }

    /**
     * Fill listWikiUrl with url based on a file filled with either full URL or just name of wikipedia pages (every pages must be of the same language)
     * @param fileName name of the file where urls are written
     * @param isFullUrl if True : the file is filled with full URL, else it's only the name of the wikipedia page
     * @param language the abreviation of the language of the targetted wikipedia page (i.e "en" for English, "fr" for French, etc.)
     */
    public void addWikiUrlFromFile(String fileName, boolean isFullUrl, String language){
        try {
            FileReader fileReader = new FileReader(System.getProperty("user.dir") + File.separator + fileName + ".txt");
            BufferedReader bufferReader = new BufferedReader(fileReader);

            System.out.println("File " + fileName + " readed correctly!");

            try {
                String line = bufferReader.readLine();
                while (line != null) {
                    if(isFullUrl){
                        addWikiUrl(line);
                    }else{ //if(isPageTitle)
                        line = "https://" + language + ".wikipedia.org/wiki/" + line;
                        addWikiUrl(line);
                    }
                    line = bufferReader.readLine();
                }

                bufferReader.close();
                fileReader.close();

            } catch (IOException exception) {
                System.out.println("Reading error : " + exception.getMessage());
            }
        } catch (FileNotFoundException exception) {
            System.out.println("File does not exist");
        }
    }

    /**
     * Add an url to listWikiUrl
     * @param httpUrl url to add to listWikiUrl
     */
    public void addWikiUrl(String httpUrl){
        try{
            listWikiUrl.add(new WikiUrl(httpUrl));
            System.out.println(httpUrl + " : Url is valid!");
        }
        catch(IllegalArgumentException argException){
            System.out.println(httpUrl + " : " + argException.getMessage());
        }
    }

    /**
     * Go through every HTMLUrl in listWikiUrl to get a list of Table for each of them,
     * then add each Tables to listTable
     */
    public void parseHTML(){
        System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
        System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
        System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
        for(int i = 0; i < listWikiUrl.size(); i++){
            System.out.println("Treating HTML url : " + listWikiUrl.get(i).getHtmlUrl());

            parserHTML.setUrlHtml(listWikiUrl.get(i).getHtmlUrl());
            ArrayList<Table> currentPageTables = parserHTML.parseHtml();

            for(int j = 0; j < currentPageTables.size(); j++){
                listTable.add(currentPageTables.get(j));
                System.out.println("+1 Table");
            }
        }
    }

    /**
     * Go through every WikiTextUrl in listWikiUrl to get a list of Table for each of them,
     * then add each Tables to listTable
     */
    public void parseWikiText(){
        System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
        System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
        System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
        for(int i = 0; i < listWikiUrl.size(); i++){
            System.out.println("Treating WikiText url : " + listWikiUrl.get(i).getWikiTextUrl());

            parserWikiText.setUrlWikiText(listWikiUrl.get(i).getWikiTextUrl());
            ArrayList<Table> currentPageTables = parserWikiText.parseWikiText();

            for(int j = 0; j < currentPageTables.size(); j++){
                listTable.add(currentPageTables.get(j));
                System.out.println("Table : " + currentPageTables.get(j).getTitle() + " added!");
            }
        }
    }

    /**
     * Go through every Table in listTable and pass it to converter which create a CSV file for each of them
     */
    public void convert(){
        System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
        System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
        System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
        System.out.println("Starting convertion to CSV");
        for(int i = 0; i < listTable.size(); i++){
            boolean isCreated = converter.convertToCSV(listTable.get(i));
            System.out.println("Table "+ listTable.get(i).getTitle() +" a été convertie : "+isCreated);
        }
        System.out.println("Convertion ended");
    }
}
