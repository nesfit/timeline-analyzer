# Timeline Analyzer
(c) 2017--2019 Radek Burget (burgetr@fit.vutbr.cz)

Timeline Analyzer is a framework for efficient analysis of social network profiles and other related data. It focuses on the analysis of security incidents related to social networks. The goal of the framework is to put together data organized in time lines coming from different heterogeneous sources, store it in a unified central storage and provide subsequent analytical and visualization steps. The framework consists of the following elements:

- **Data sources** -- social network profiles or local browser profiles. The following locally running adapters are available in this repository:
  - [Twitter downloader](https://github.com/nesfit/timeline-analyzer/tree/master/timeline-analyzer-twitter) -- downloads events from Twitter profiles
  - [Facebook downloader](https://github.com/nesfit/timeline-analyzer/tree/master/timeline-analyzer-fb) -- downloads events from public Facebook profiles
  - [Local browser profile decoder](https://github.com/nesfit/timeline-analyzer/tree/master/timeline-analyzer-local) -- decodes local browser profiles and extracts the events
- **Unified data storage** -- a unified data model based on semantic web technology and a central storage (currently a [RDF4J](http://rdf4j.org/) repository for running on a centralized server or [Halyard](https://merck.github.io/Halyard/) for running on a Hadoop cluster).
- **Visualization tools** --  a web-based GUI for reviewing the gathered data. It is a separate angular-based application that runs in a web browser. It connects the RDF repository and provides a graphical interface for browsing the time lines.

A distributed social network profile downloader based on [Apache Spark](https://spark.apache.org/) is available as a separate [Socializer](https://github.com/nesfit/socializer) project.


## Installation and Running

See the [wiki pages](https://github.com/nesfit/timeline-analyzer/wiki) for details.

## Acknowledgements

*This work was supported by the Ministry of the Interior of the Czech Republic as a part of the project Integrated platform for analysis of digital data from security incidents VI20172020062.*
