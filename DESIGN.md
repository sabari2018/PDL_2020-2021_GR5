## Design 

This project contains two packages : one "model" which contains all classes and "test" which contains all test classes.

The global architecture is : 



<Insert Diagramme de class>
  
  
  But we need to do modification on this architecture
  
  
# Dynamic model
  
Actually, the project starts when Main is executed. This class allows to start a Wikipedia table extraction from two different formats : HTML or Wikitext. 

The nominal scenario represents a Main execution without errors :
 
![100% center](images/sequence-diagram.png)

First a "Fichier" object is created. This object allows to do operations on an extracted Wikipedia page.

The first method "productUrls()" lists all urls in a file. After the method "fichierToHTML ()" realizes HTML extraction. In the second part a new "Fichier" object is created and the method "productUrlsWikitext" creates a file with all Wikitext urls. At the end, "FichierToWikitext" realizes the Wikitext extraction.

