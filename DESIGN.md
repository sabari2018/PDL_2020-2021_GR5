## Design 

This project contains two packages : one "model" which contains all classes and "test" which contains all test classes.

The global architecture is : 



<Insert Diagramme de class>
  
  
  But we need to do modification on this architecture
  
  
#Dynamic model
  
Actually, the project starts when Main is executed. This class permits to start an Wikipedia table extraction with two methods : by HTML and Wikitext. 

The nominal scenario represents a Main execution without errors :
 
![100% center](images/sequence-diagram.png)

First an Fichier object is created. This object permits to do operations on Wikipedia page.

The first method "productUrls ()" lists all urls in a file. After the method "fichierToHTML ()" realizes HTML extraction. In the second part a new Fichier object is created and the method "productUrlsWikitext" creates a file with all wikitext urls. At the end, "FichierToWikitext" realizes wikitext extraction.
