package model;

import java.io.IOException;
import model.ParserHTML;

public class Main {

    public static void main(String[] args) throws IOException {
        ParserHTML p = new ParserHTML("https://en.wikipedia.org/wiki/Comparison_between_Esperanto_and_Interlingua");
    }

}
