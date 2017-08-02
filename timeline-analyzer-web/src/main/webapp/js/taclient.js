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
	return this.client.getObjectsWhere('?s rdf:type ta:Entry . ?s ta:sourceTimeline <' + timelineUri + '>');
};

TAClient.prototype.loadEntry = function(entry) {
	for (prop in entry) {
		if (entry.hasOwnProperty(prop) && typeof entry[prop] === 'object' && entry[prop].uri !== undefined) {
			var uri = entry[prop].uri;
			entry[prop] = this.client.getObject(uri);
		}
	}
};
