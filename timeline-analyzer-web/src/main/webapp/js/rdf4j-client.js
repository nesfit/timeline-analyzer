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
var RDFClient = function(serverUrl, repositoryName, namespaces) {
	this.url = serverUrl;
	this.repo = repositoryName;
	this.ns = namespaces;
	this.defaultNS = 'rdf';
};

RDFClient.prototype.setDefaultNamespace = function(ns) {
	this.defaultNS = ns;
}

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
	    	success(data);
	    },
	    error: error
	});			
};

/**
 * Executes a query on the server.
 */
RDFClient.prototype.getObjectsWhere = function(where, success, error = null) {
	var client = this;
	var w = '?s ?p ?o';
	if (where)
		w += ' . ' + where;
	var query = this.getPrefixes() + 'SELECT ?s ?p ?o WHERE {' + w + '}';
	console.log('Q: ' + query);
	this.sendQuery(query, function(data) {
			success(client.parseResponseObjects(data));
		},
		error);
}

/**
 * Parses the RDF4J server response and creates a set of object with the given properties.
 */
RDFClient.prototype.parseResponseObjects = function(data) {
	var ret = {};
	var bindings = data.results.bindings;
	for (var i = 0; i < bindings.length; i++) {
		var item = bindings[i];
		if (item.s.type == 'uri') { //process only URI subjects
			var subject = item.s.value;
			var property = this.getPropertyName(item.p.value);
			var value;
			if (item.o.type == 'uri')
				value = { uri: item.o.value };
			else
				value = item.o.value;
			
			if (ret[subject] === undefined)
				ret[subject] = {};
			ret[subject][property] = value;
		}
	}
	return ret;
};

RDFClient.prototype.getPrefixes = function() {
	var ret = '';
	for (var ns in this.ns) {
		if (this.ns.hasOwnProperty(ns)) {
			console.log('prefix ' + ns)
			ret += 'PREFIX ' + ns + ': <' + this.ns[ns] + '>\n';
		}
	}
	return ret;
}

/**
 * Computes an object property name from the property URI according to the currently
 * configured namespaces.
 */
RDFClient.prototype.getPropertyName = function(uri) {
	for (var ns in this.ns) {
		if (this.ns.hasOwnProperty(ns)) {
			var prefix = this.ns[ns];
			if (uri.startsWith(prefix)) {
				var name = uri.substring(prefix.length);
				if (ns == this.defaultNS)
					return name;
				else
					return ns + '_' + name;
			}
		}
	}
	return uri;
};
