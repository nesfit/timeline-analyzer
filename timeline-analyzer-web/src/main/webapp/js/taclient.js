/*
 * taclient.js
 * Timeline analyzer client JS library.
 * 
 * (c) Radek Burget 2017
 * 
 */

var TAClient = function(serverUrl, repositoryName) {
	
	//RDF namespaces used
	this.ns = {
			ta: 'http://nesfit.github.io/ontology/ta.owl#',
			rdf: 'http://www.w3.org/1999/02/22-rdf-syntax-ns#',
			rdfs: 'http://www.w3.org/2000/01/rdf-schema#',
			tares: 'http://nesfit.github.io/resource/ta#'
		};

	this.url = serverUrl;
	this.repo = repositoryName;
	this.client = new RDFClient(this.url, this.repo, this.ns);
	this.client.setDefaultNamespace('ta');
};

TAClient.prototype.getTimelines = function() {
	return this.client.getObjectsWhere('?s rdf:type ta:Timeline');
};

TAClient.prototype.getEntries = function(timelineUri) {
	return this.client.getObjectArrayWhere('?s rdf:type ta:Entry . ?s ta:timestamp ?time . ?s ta:sourceTimeline <' + timelineUri + '>', 'ASC(?time)');
};

/**
 * Loads all the entry contents.
 * @return a set of promises.
 */
TAClient.prototype.loadEntryContents = function(entry) {
	var cont = entry.contains;
	if (!Array.isArray(cont))
		cont = [ cont ];
	var promises = [];
	for (var i = 0; i < cont.length; i++) {
		promises[promises.length] = this.client.getObject(cont[i].uri);
	}
	return promises;
};
