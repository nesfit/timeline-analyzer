/*
 * gui.js
 * (c) Radek Burget 2017
 */

var GUI = {

	createTimelineElement: function(parent) {
		var ret = $('<div class="timeline panel panel-primary"></div>');
		parent.append(ret);
		return ret;
	},

	addTimeline: function(uri, parentRow) {
		var col = $('<div class="col-md-4"></div>');
		parentRow.append(col);
		var panel = this.createTimelineElement(col);
		var timeline = new GUITimeline(uri, panel)
	}
	
}

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
	//console.log(entries);
	for (var i = 0; i < entries.length; i++) {
		this.addEntry(entries[i]);
	}
};

GUITimeline.prototype.addEntry = function(entry) {
	var elem = $('<div class="entry"><div>').attr('resource', entry.URI);
	elem.append($('<div class="timestamp"></div>').text(entry.timestamp));
	this.el.append(elem);
	
	Promise.all(TA.loadEntryContents(entry)).then(function(values) {
		for (var i = 0; i < values.length; i++) {
			var val = values[i];
			var type = TA.client.getRDFObjectType(val);
			
			var div = $('<div class="content"></div>');
			div.attr('resource', val.URI);
			if (type == "TextContent") {
				div.addClass('text');
				div.text(values[i].text);
				elem.append(div);
			} else if (type == "Image") {
				div.addClass('image');
				div.append('<a href="' + val.sourceUrl + '"><img src="' + val.sourceUrl + '" alt=""></a>');
				elem.append(div);
			} else {
				div.addClass('unknown');
				elem.append($('<div class="content"></div>').text('Unknown ' + type));
			}
		}
	});
	
}