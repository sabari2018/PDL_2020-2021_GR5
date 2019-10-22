package testsProjetOriginaux;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import modelOrigin.Url;

/**
 * Classe de tests de la classe Url
 * @author Romiche
 *
 */
public class TestUrl {

	Url url = new Url("https://fr.wikipedia.org/wiki/Coupe_du_monde_de_football");
	/**
	 * Test de la méthode isValidUrl() 
	 * Sur un url valide
	 */
	@Test
	public void TestValidUrl() {
		assertTrue(url.isValidUrl());
	}

	/**
	 * Test de la méthode isValidUrl() 
	 * Sur un url à redirigé
	 */
	@Test
	public void TestRedirectUrl() {
		url.setUrl("https://fr.wikipedia.org/wiki/Coupe du monde de football"); // Lien redirigÃ©
		assertTrue("Lien non redirigé", url.isValidUrl());
	}

	/**
	 * Test de la méthode isValidUrl()
	 * Sur un url non valide
	 */
	@Test
	public void TestNonValidUrl() {
		url.setUrl("http://fr.wikipedia.org/wiki/Coupe de football"); // Lien non valide
		assertFalse("Lien non valide", url.isValidUrl());
	}

	/**
	 * Test de la méthode isValidUrl()
	 * Sur un wikiUrl valide
	 */
	@Test
	public void testValidWikiUrl(){
		url.setUrl("https://fr.wikipedia.org/wiki/(29088)_1981_DR2?veaction=edit");
		assertTrue("valide WikiText url considere invalide!", url.isValidUrl());
	}

	/**
	 * Test de la méthode isValidUrl()
	 * Sur un wikiUrl non valide
	 */
	@Test
	public void testInvalidWikiUrl(){
		url.setUrl("https://fr.wikipedia.org/wiki/(2988)_181_DR2?veaction=edit");
		assertFalse("invalide WikiText url considere valide!", url.isValidUrl());
	}

	/**
	 * Test de la méthode isValidUrl()
	 * Sur un url null
	 */
	@Test
	public void testUrlNull(){
		url.setUrl("");
		assertFalse("url vide considere valide!", url.isValidUrl());
	}

	/**
	 * Test de la méthode isValidUrl()
	 * Sur un url chinois
	 */
	@Test
	public void testChineseUrl(){
		url.setUrl("https://zh.wikipedia.org/wiki/%E7%94%9C%E5%BF%83%E5%A4%A7%E8%A9%B1%E7%8E%8B");
		assertTrue("valid chinese url is not recognized", url.isValidUrl());
	}

	@Test
	public void TestToHTML() throws IOException{
		System.out.println(url.HTML());
	}
}
