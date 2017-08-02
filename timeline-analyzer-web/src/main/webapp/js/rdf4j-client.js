/**
 * rdf4j-client.js
 * A client libraty for the RDF4J server.
 * 
 * (c) 2017 Radek Burget <burgetr@fit.vutbr.cz>
 * 
 */


/**
 * Creates a new RDF client.
 */
var RDFClient = function(serverUrl, repositoryName) {
	this.url = serverUrl;
	this.repo = repositoryName;
};

/**
 * Checks the connection to the RDF4J server.
 * @return true or false 
 */
RDFClient.prototype.checkConnection = function() {
	var ret = false;
	$.ajax({
	    url: this.url + '/protocol',
	    type: 'GET',
	    async: false,
	    success: function(msg) {
	    	ret = true;
	    }
	});
	return ret;
};

/**
 * Executes a query on the server.
 */
RDFClient.prototype.sendQuery = function(query, success, error = null) {
	var client = this;
	$.ajax({
	    url: this.url + '/repositories/' + this.repo,
	    type: 'POST',
	    data: query,
	    contentType: 'application/sparql-query',
	    dataType: 'json',
	    headrs: {
	    	Accept: 'application/sparql-results+json'
	    },
	    async: true,
	    success: function(data) {
	    	success(client.parseResponse(data));
	    },
	    error: error
	});			
};

RDFClient.prototype.parseResponse = function(data) {
	return data; //TODO
};


