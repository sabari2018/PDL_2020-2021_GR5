package testsProjetOriginaux;

import static org.junit.Assert.*;

import java.io.*;

import org.junit.Before;
import org.junit.Test;

import modelOrigin.FormatWikitext;
import modelOrigin.Url;

/**
 * Classe de tests de la classe FormatWikitext
 *
 * @author Romiche
 *
 */
public class TestWikitext {

	//Contient un tableau
	private final Url url = new Url("https://en.wikipedia.org/wiki/Comparison_of_BitTorrent_tracker_software");
	//Contient 6 tableaux
	private final Url url2 = new Url("https://en.wikipedia.org/wiki/Comparison_of_Norwegian_Bokmal_and_Standard_Danish");
	// final Url url = new
	// Url("https://fr.wikipedia.org/wiki/Internationaux_de_France_de_tennis");
	private FormatWikitext wikitext = new FormatWikitext();
	private FormatWikitext wikitext2 = new FormatWikitext();

	/**
	 * Methode permettant la simplification de la classe de Test
	 *
	 * @throws IOException
	 */

	//VERIFIER A QUEL MOMENT CHANGEMENT DE CARACTERE ENCODER POUR LE "A" rond (avant ou apres HTML) pb de nb tableau2 qd A a un accent bizarre

	@Before
	public void need() throws IOException {
		wikitext = new FormatWikitext(url.HTML());
		wikitext2 = new FormatWikitext(url2.HTML());
	}

	/**
	 * Test de la methode newUrl()
	 * @return l'Url de la page wikitext après avoir récupéré celui de la page wikipédia correspondante
	 * @throws IOException
	 */
	@Test
	public void TestNewUrl() {
		assertEquals(wikitext.recupererURL().toString(), "https://en.wikipedia.org/w/index.php?title=Comparison of BitTorrent tracker software&action=edit");
	}

	@Test
	public void TestNewUrl2() {
		assertEquals(wikitext2.recupererURL().toString(), "https://en.wikipedia.org/w/index.php?title=Comparison of Norwegian Bokmål and Standard Danish&action=edit");
	}

	/**
	 * Test général des méthodes wikitext........
	 * @throws IOException
	 */
	/*@Test
	public void testwiki() throws IOException {
		wikitext.initialize(url);
	}*/

	/**
	 * Test de la methode wikiCountTabs()
	 * @return le nombre de tableaux présents sur la page wikipédia
	 * @throws IOException
	 */
	@Test
	public void TestNbTableaux() {
		assertEquals(wikitext.wikiCountTabs(), 1);
	}

	@Test
	public void TestNbTableaux2() {
		assertEquals(wikitext2.wikiCountTabs(), 6);
	}
	/**
	 * Test de la méthode wikiNombreLigne()
	 * @return le nombre de lignes de chaque tableau de la page
	 * @throws IOException
	 */
	@Test
	public void TestNbLignes() throws IOException {
		//Méthode à revoir.... car elle split sur les scope=row
		//System.out.println(wikitext.nblignes);
		assertEquals(wikitext.wikiNombreLigne(), 9);
	}

	/**
	 * Test de la méthode getTitle()
	 * @return le titre
	 * @throws IOException
	 */
	@Test
	public void TestgetTitle() throws IOException {
		//System.out.println("titre : "+ wikitext.getTitle());
		assertEquals(wikitext.getTitle(), "Comparison of BitTorrent tracker software" );
	}

	/**
	 * Test de la méthode clone()
	 * @return le clone wikitext
	 * @throws IOException
	 */
	@Test
	public void TestClone() throws IOException {
		//System.out.println("clone : "+ wikitext);
		assertEquals(wikitext.clone().toString(), wikitext.toString());
	}

	/**
	 * Test de la méthode getTableau()
	 * @return le tableau en format wikitext
	 * @throws IOException
	 */
	@Test
	//Methode cassé
	public void TestGetTab() throws IOException {
		System.out.println("tab : "+ wikitext.getTableau());
		//assertEquals(wikitext.getTableau(), wikitext.getT);
	}



}
