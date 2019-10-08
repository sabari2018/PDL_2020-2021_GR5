## Design 

This project contains two packages : one "model" which contains all classes and "test" which contains all test classes.

The global architecture is : 



<Insert Diagramme de class>
  
  
  But we need to do modification on this architecture
  
  
  #Dynamic model
  
Actually, the project starts when Main is executed. This class permits to start an Wikipedia table extraction with two methods : by HTML and Wikitext. Actually the HTLM extraction runs go to the end even if this execution catch exeception. However the Wikitext extraction doesn't run competely. This extraction ends when a FileNotFoundException is throws.

The execution in the main is :
