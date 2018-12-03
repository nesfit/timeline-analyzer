# Timeline Analyzer
(c) 2017--2018 Radek Burget (burgetr@fit.vutbr.cz)

Timeline Analyzer is a framework for efficient analysis of social network profiles and other related data. It focuses on the analysis of security incidents related to social networks. The goal of the framework is to put together data organized in time lines coming from different heterogeneous sources, store it in a unified central storage and provide subsequent analytical and visualization steps. The framework consists of the following elements:

- **Data sources** -- social network profiles, local browser profiles, ...
- **Unified data storage** -- a unified data model based on semantic web technology and a central storage (currently a [RDF4J](http://rdf4j.org/) repository)
- **Analytical steps** -- an extensible set of algorithms for the gathered data analysis
- **Visualization tools** --  a web-based GUI for reviewing the gathered data

*This work was supported by the Ministry of the Interior of the Czech Republic as a part of the project Integrated platform for analysis of digital data from security incidents VI20172020062.*
