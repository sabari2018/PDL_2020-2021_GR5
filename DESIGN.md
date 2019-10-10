## Design 


# Global architecture

This project contains two packages : one "model" which contains all classes and "test" which contains all test classes.

The global architecture is : 

![100% center](images/class_diagram_origin_V2.0.png)

We spotted some issues like “useless” inheritance. On the diagram, we can see “Fichier” extends “Url” which extends “FormatHTML”. Inheritance normally allows to factorize common concepts shared by different subclasses in a single superclass. It allows also to isolate the concepts proper to each subclass and to redefine superclass operations if necessary. 
We observed the code and we realized none of these concepts seemed to be applied. We did not see to what extent “FormatHTML”, “Url” and “Fichier” had so much common concepts. The inheritance did not follow any software logic. Moreover, if we removed the inheritance, the program was running with no difference.
We also spotted unused methods or attributes. We need to check if they have been forgotten or if they are just the beginning of unfinished implementations. After that we will remove unnecessary code to have a clean project.

Some operations do not follow traditional coding conventions. All operations, except constructors should not start with a capital letter. For example, the « PremierSplit() : FormatHTML » method in the FormatHTML class should be written « premierSplit() : FormatHTML ». This problem can be spotted on several operations in the code.
Some class are not correctly named, especially the Url class which can be confused with the java Url object. This class should bear a different name to avoid any misunderstandings.
The project makes the use of a mix between French and English. It is not a correct practice. Some methods are in English like « headToCSV() : ProductionCSV » in the FormatWikiText class. Some are in French like « NombreCol() : int » in the FormatHTML class. Others are using a mix between French and English like the PremierSplit() : FormatHTML method in the FormatHTML class. There is a similar problem with the class names. We should program using a single language (English is a better solution as it is widely spoken, so easily understood by most of foreign developers) to avoid comprehension problems.
A few attribute names should be changed because their name is not explicit. For example, the attribute « urlUrl : Url » of the « Fichier » class constructor. 
Even if not causing technical difficulties, these coding conventions issues can cause misunderstanding for future developers and should be correcting using code refactoring with the IDE.

# Class descriptions

| Class | Extends | Description | 
| --- | --- | --- |
| Fichier | Url, FormatHTML, Thread (Java class) | Produces the different Wikipedia pages urls, triggers the HTML and Wikitext extraction |
| FormatHTML | Thread (Java class) | Extracts the HTML tables form the Wikipedia pages and calls the CSV conversion methods |
| FormatWikiText |  | Extracts the wikitext tables form the Wikipedia pages and calls the CSV conversion methods |
| Main |  | Runs the program creates "Fichier" Objects |
| ProductionCSV |  | Deals with the conversion of HTML and Wikitext tables into CSV |
| Url | FormatHTML, Thread (Java class) | Contains the methods to test if the urls are valid |

  
## Dynamic model
  
Actually, the project starts when Main class is executed. This class allows to start a Wikipedia table extraction from two different formats : HTML or Wikitext. 

The nominal scenario represents a Main execution without errors :
 
![100% center](images/diagramme_sequence.png)

In the main, a Fichier object is created. This object contains a Set<Url> attribute, called ‘setUrl’ grouping all Wikipedia urls. This object will permit to do operations on Wikipedia page.

First, productUrls () is called and fill ‘setUrl’. For that, this method read ‘wikiurls.txt’ which contains all Wikipedia pages that we want treat. Each page is add to the Set with this form:  https://en.wikipedia.org/wiki/ + page name.

Then FichierToHTML () is executed and creates a CSV file. This file contains the HTML extraction from Wikipedia table contents. This file is present in ‘output/html’.

To realize the table extraction form wiki text, a Fichier object is recreated to reinitialize ‘setUrl’ attribute.

After, productUrlsWikitext ()is calling. This method has the same role that productUrls () but on this way, urls are stocked on this forme : https://en.wikipedia.org/w/index.php?title= + page name + &action=edit.

At the end, FichierToWikitext() is executed. This méthode creates a CSV file. This file contains the HTML extraction from Wikipedia table contents and is present in ‘output/html’.

## Execution scenarios

The rated scenario consists to realize the extraction of all tables contains on all Wikipedia pages defines in ‘wikiurls.txt’ in two different ways: using wiki text or HTML code. A successful extraction is, for all pages, a CSV file correctly established. That is to say a first line with the table column name and the other lines corresponding to table datas. The CSV must have a comma like separator.


# Tests Results

Tests results realised on 10/08/19

| <h3>Test name</h3>        |  <h3>Finale Description</h3>      |
|:------:|-----|
| **TestCSV**  	| Empty 	|
| **TestFichier**  	|||
| <p style="color:#FFFF00;">TestRemove2 </p>	| OK 	|
| TestUrlFromFile  	| OK 	|
| TestAdd2  	| OK 	|
| TestToString  	| OK 	|
| TestRemove  	| OK 	|
| TestReadFile  	| Failed => Assertion Error : <ul><li>Expected : 312</li> <li>Actual : 314</li></ul>	|
| TestAdd  	| OK 	|
| **TestHTML**  	| |
| Testhtml  	| Error => Array Index Out Of Bounds Exception : Index 1 out of bounds for lenght 1 	|
| TestTitle  	| Failed => Comparison failure 	|
| TestNbColonnes  	| Failed => Assertion Error : <ul><li>Expected : 1</li> <li>Actual : 10</li></ul> 	|
| **TestPDL**	| |
|  TestWiki	| Error  => null pointer exception	|
| **TestURL**	| |
|  TestToHTML  	|  OK	|
|  TestValideUrl  	| Failed => AssertionError 	|
|  TestNonValidUrl  	| OK 	|
| TestRedirectUrl  	| Failed => Assertion Error : lien redirigé	|
| **TestWikiText**  	| |
| TestNbLignes  	| Failed => Assertion Error : <ul><li>Expected : 0</li> <li>Actual : 9</li></ul>	|
| TestNewUrl  	| OK	|
| TestNbTableau  	| OK	|
