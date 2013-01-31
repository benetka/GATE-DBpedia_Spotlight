#GATE - DBpedia Spotlight plugin

DBpedia Spotlight plugin for GATE framework.

## Installation guide 
1.  Clone out the master branch of https://github.com/jendarybak/GATE-DBpedia_Spotlight
```
git clone https://github.com/jendarybak/GATE-DBpedia_Spotlight
```

2.  Copy this plugin folder into GATE's folder for plugins (GATE/plugins).
3.  Run ANT script (in console navigate to the folder and type 'ant'). If [ANT](http://ant.apache.org/) is not installed, you may have to [install it](http://ant.apache.org/bindownload.cgi).
```
ant
```
4.  In GATE Developer open 'CREOLE Plugin Manager' (File -> Manage CREOLE Plugins).
5.  Find plugin called 'Tagger_DBpediaSpotligh'.
6.  Check 'Load now' (or/and 'Load always').
7.  Confirm by clicking 'Apply All'.

## Running
1.  In GATE right-click on 'Processing Resources' in the left menu.
2.  Select 'New' and find 'DBpedia Spotlight' in the menu, click on it.
3.  A window will appear and you may give the processing resource a name and you have to choose DBpedia Spotlight web service URL (default should be prefilled).
4.  When you add DBpedia Spotlight processing resource to you pipeline, you can customize it by setting parameters.

## Parameters

*   __confidence__

    > Double <0-1>
    > Disambiguation parameter. Combines factors of topic relevance and context ambiguity. It’s value is ranging from 0 to 1 and higher threshold results in omitting incorrect annotations at the risk of loosing some correct annotations.
*   __coreferenceResolution__

    > Boolean
    > Should be coreference resolution applied?
*   __disambiguator__

    > String (Document | Occurrences)
*   __outputASName__

    > String
    > Name of the annotation set used for the output
*   __policy__

    > String (blacklist | whitelist)
    > Policy for given parameters.
*   __sparql__

    > String
    > [SPARQL](http://www.w3.org/TR/sparql11-query/) query to filter results ([example](https://github.com/dbpedia-spotlight/dbpedia-spotlight/wiki/Web-service)).
*   __support__

    > Integer
    > Minimal number of Wikipedia backlinks.    
*   __types__

    > String
    > Specification of RDF types such as DBpedia:Company.            

## Other
Examples of using DBpedia Spotlight web service can be found here: https://github.com/dbpedia-spotlight/dbpedia-spotlight/wiki/Web-service.

#### DBpedia Spotlight Description

DBpedia Spotlight is a tool for automatically annotating mentions of DBpedia resources in text, providing a solution for linking unstructured information sources to the Linked Open Data cloud through DBpedia. DBpedia Spotlight recognizes that names of concepts or entities have been mentioned (e.g. "Michael Jordan"), and subsequently matches these names to unique identifiers (e.g. [dbpedia:Michael_I._Jordan](http://dbpedia.org/page/Michael_I._Jordan), the machine learning professor or [dbpedia:Michael_Jordan](http://dbpedia.org/page/Michael_Jordan) the basketball player). It can also be used for building your solution for [Named Entity Recognition](http://en.wikipedia.org/wiki/Named_entity_recognition), Keyphrase Extraction, Tagging, etc. amongst other information extraction tasks.

More: https://github.com/dbpedia-spotlight/dbpedia-spotlight

#### GATE Description

GATE is a natural language processing framework used for all types of computational task involving human language. GATE is the biggest open source language processing project.

More: http://gate.ac.uk/