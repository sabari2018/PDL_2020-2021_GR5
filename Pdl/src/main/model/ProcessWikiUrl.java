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
     * @return listWikiUrl
     */
    public List<WikiUrl> getListWikiUrl() {
        return listWikiUrl;
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
                System.out.println("Erreur de la lecture : " + exception.getMessage());
            }
        } catch (FileNotFoundException exception) {
            System.out.println("Le fichier n'existe pas");
        }
    }

    /**
     * Add an url to listWikiUrl
     * @param httpUrl url to add to listWikiUrl
     */
    public void addWikiUrl(String httpUrl){
        try{
            listWikiUrl.add(new WikiUrl(httpUrl));
        }
        catch(IllegalArgumentException argException){
            System.out.println(httpUrl + " : " + argException.getMessage());
        }
    }

    public void parseHTML(){

    }

    /**
     * Go through every WikiTextUrl in listWikiUrl to get a list of Table for each of them,
     * then add each Tables to listTable
     */
    public void parseWikiText(){
        for(int i = 0; i < listWikiUrl.size(); i++){
            parserWikiText.setUrlWikiText(listWikiUrl.get(i).getWikiTextUrl());
            ArrayList<Table> currentPageTables = parserWikiText.parseWikiText();

            for(int j = 0; j < currentPageTables.size(); j++){
                listTable.add(currentPageTables.get(j));
            }
        }
    }

    public void Convert(){

    }
}
