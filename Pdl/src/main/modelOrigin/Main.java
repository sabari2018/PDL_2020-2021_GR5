package modelOrigin;
import modelOrigin.Fichier;

import java.io.IOException;

public class Main {
	
	

	public static void main(String[] args) throws IOException {
		
		System.out.println("-------------------------- DEBUT TRAITEMENT HTML --------------------------");
		System.out.println("");
		Fichier f = new Fichier();
		f.productUrls();
		f.FichierToHTML();
		f = new Fichier();
		System.out.println("");
		System.out.println("-------------------------- FIN TRAITEMENT HTML --------------------------");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("-------------------------- DEBUT TRAITEMENT WIKITEXT --------------------------");
		f.productUrlsWikitext();
		f.FichierToWikitext();
		System.out.println("-------------------------- FIN TRAITEMENT WIKITEXT --------------------------");
	}

}
