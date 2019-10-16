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

The software takes a file with a list of wikipedia's pages title (taken from the page URL : with "\_" in place of " ") and process each  one to get the HTML and WikiText URL of the page.
After testing the URL: it treat all the HTML code of each page and try to extract as much table as it can in CSV and then do the same for WikiText.

Right now, the HTML treatment seems to work correctly, but the WikiText part always break at some point due to an unknown error.


## Functionnality to develop

We now have to fix the WikiText extraction for it to work correctly.

We would also have to create some new test in JUnit.

We could then try to improve the software itself for it to run with better performances.

In the end we need to implement a tool that will allow us to automatically evaluate tables extractions based on preset results and tests.



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
