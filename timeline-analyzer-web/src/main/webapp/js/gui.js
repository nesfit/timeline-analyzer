/*
 * gui.js
 * (c) Radek Burget 2017
 */

var createTimelineElement = function(parent) {
	var ret = $('<div class="timeline panel panel-default"></div>');
	parent.append(ret);
	return ret;
};

//==================================================================================

var GUITimeline = function(uri, element) {
	this.uri = uri;
	this.el = element;
	
	var client = this;
	TA.client.getObject(this.uri).then(function(timeline) {
		//init the title etc
		client.initTimeline(timeline);
		//load and add the entries
		console.log('LOAD ' + client.timeline);
		console.log(client.timeline);
		TA.getEntries(client.timeline.URI).then(function (entries) {
			client.addEntries(entries);
		});
	});
	
};

GUITimeline.prototype.initTimeline = function(timeline) {
	this.timeline = timeline;
	console.log('init uri: ' + this.uri);
	
	//create panel header
	var title = this.timeline.rdfs_label;
	if (!title) title = this.uri;
	this.el.append('<div class="panel-heading"><h3 class="panel-title" title="' + this.uri + '">' + title + '</h3></div>');
};

GUITimeline.prototype.addEntries = function(entries) {
	for (euri in entries) {
		if (entries.hasOwnProperty(euri)) {
			this.addEntry(entries[euri]);
		}
	}
};

GUITimeline.prototype.addEntry = function(entry) {
	var elem = $('<div class="entry"><div>');
	elem.append($('<div class="timestamp"></div>').text(entry.timestamp));
	this.el.append(elem);
}
