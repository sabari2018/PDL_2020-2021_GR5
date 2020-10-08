# Extractors evaluation
During this project, two types of extractors has been made. One of them extract tables from HTML code. 
The other extract from WikiText code. We have to compare the performance of the two extractors. 
The first comparison will be on the number of URL processed. 
The other comparison will be on the quality of the extraction.

## Number of extraction

Like we said before, we have to compare the number of good extraction.
There is 303 URL processed for 336 in total.

### Comparison

|HTML Extraction|WikiText Extraction|
|:----------:|:---------:|
|1657|1049|
 
### List of problems
|<h3>Problems </h3>| <h3>HTML Extractor</h3>       |  <h3>Wikitext Extractor</h3>  |
|:---------------:|:------------:|:------------:| 
|Bad extract of headers of rows| X ||
|When the value of cells is a link, it extract the content| X ||
|Merged cells create a new column for each rows| X | X |
|When cells are merged, only one row include the value| X | X |
|Some cells in colors extract the color attribute|| X |
|When the value of cells is "?", it extract "dunno"| | X |
