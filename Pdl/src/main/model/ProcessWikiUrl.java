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

    public List<WikiUrl> getListWikiUrl() {
        return listWikiUrl;
    }

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

    public void parseWikiText(){

    }

    public void Convert(){

    }
}
