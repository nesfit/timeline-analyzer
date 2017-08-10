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
	
	this.dateStart = null;
	this.dateEnd = null;
	
	this.linkProperties = {};
	this.loadMetadata();
};

TAClient.prototype.setDateSpan = function(dateStart, dateEnd) {
	this.dateStart = dateStart;
	this.dateEnd = dateEnd;
};

TAClient.prototype.getTimelines = function() {
	return this.client.getObjectsWhere('?s rdf:type ta:Timeline');
};

TAClient.prototype.getEntries = function(timelineUri) {
	var filter = '';
	if (this.dateStart && this.dateEnd) {
		filter = ' FILTER (?time >= "' + this.dateStart.toISOString() + '"^^xsd:dateTime && ?time <= "' + this.dateEnd.toISOString() + '"^^xsd:dateTime)';
	}
	return this.client.getObjectArrayWhere('?s rdf:type ta:Entry . ?s ta:timestamp ?time . ?s ta:sourceTimeline <' + timelineUri + '>' + filter, 'ASC(?time)');
};

TAClient.prototype.loadMetadata = function() {
	var self = this;
	this.client.getObjectArrayWhere('?s rdfs:subPropertyOf ta:contextLink').then(function(props) {
		for (var i = 0; i < props.length; i++) {
			var prop = props[i];
			var name = self.client.getPropertyName(prop.URI);
			var label = prop.rdfs_label ? prop.rdfs_label : name;
			self.linkProperties[name] = label;
		}
	});
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

TAClient.prototype.loadLinks = function(uri) {
	//var where = "?s ?p ?o . <" + uri + "> ?link ?s . ?link rdfs:subPropertyOf ta:contextLink";
	//var where = "?s ?p ?o . <" + uri + "> ?p ?o . ?p rdfs:subPropertyOf ta:contextLink";
	//return this.client.getObjectArrayWhere(where);
	
	var q = "SELECT ?p ?o ?label ?dtime ?text ?src ?srclabel"
		+ " WHERE {<" + uri + "> ?p ?o . ?p rdfs:subPropertyOf ta:contextLink ." 
		+ " ?p rdfs:label ?label ." 
		+ " ?o ta:timestamp ?dtime ."
		+ " ?o ta:contains ?textc . ?textc rdf:type ta:TextContent . ?textc ta:text ?text ."
		+ " ?o ta:sourceTimeline ?src . ?src rdfs:label ?srclabel}";
	return this.client.queryObjects(q);
	
};
