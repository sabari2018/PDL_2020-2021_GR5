# Wikipedia Matrix : The Truth (2019 - 2020)

This project is held at the University of Rennes 1, ISTIC, in Master 1 MIAGE.

## Project context

This project is a fork of « Wikipedia Matrix » started by the last group of M1 MIAGE (2018 - 2019). We have to correct it and improve it. 

### What is the project « Wikipedia Matrix » ?

The goal of "Wikipedia Matrix" is to collect and extract Wikipedia's tables. These tables are on several Wikipedia pages retrieved by their url. The content of these tables will be returned in files using CSV format.

We read and analyze two Wikipedia page formats :
* HTML
* Wikitext


## Objectives

3 expected results :
* Improvement of extractors. (source code, documentation, tests, continuous integration...)
* Tools which allow us to evaluate our extractors.
* Make the project easy to use by anyone. (with a complete and functional dataset)


## Actual functionality

If you give a Wikipedia page link that contains tables, the software will generate a CSV for every valid tables in the page and exclude the ones that cannot be converted into CSV. 

If you give a Wikipedia page link that does not contain any valid tables or no tables at all, the software will inform you of this and will not generate any file. 

If you give any other link than Wikipedia, the software will tell you that the page you provide isn't compatible and won't generate any file. 

At every steps you can check the list of links that you provided to the software and check if and how many table it can extract. 


## Functionnality to develop

We have to create tools which allow us to test and evaluate our extractors.


## Project license

This project is licensed under the MIT License.


## Technologies used

* Git – The distributed version-control system used.
* IntelliJ IDEA - The IDE mainly used by our crew.
* Maven - Dependency Management. 
* Jsoup - The java based HTML Parser.
* JUnit - The unit testing framework used.
* starUML and GenMyModel - UML editors. 
* Word - The document editor used to create the specifications. 


## Authors

Yaëlle Dubois, Maud Garçon, Emile Georget, Hélène Heinlé, Saly Knab, Edgar Lebreton.
