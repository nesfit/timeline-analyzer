/*
 * gui.js
 * (c) Radek Burget 2017
 */

var GUI = {

}

//==================================================================================

var GUITimelineSet = function(parentElement) {
	this.parent = parentElement;
	this.timelines = [];
	this.timeAxis = new GUITimeAxis(this.parent);
};

GUITimelineSet.prototype.addTimeline = function(uri) {
	var col = $('<div class="col-md-4"></div>');
	this.parent.append(col);
	var panel = this.createTimelineElement(col);
	var timeline = new GUITimeline(this, uri, panel);
	this.timelines.push(timeline);
};

GUITimelineSet.prototype.recomputeOffsets = function() {
	//collect all entries
	entries = [];
	for (var i = 0; i < this.timelines.length; i++) {
		Array.prototype.push.apply(entries, this.timelines[i].entries);
	}
	//sort them by date
	entries.sort(function(a, b) {
		var da = Date.parse(a.timestamp);
		var db = Date.parse(b.timestamp);
		return da - db;
	});
	//reset values
	for (var i = 0; i < entries.length; i++)
		entries[i].GUIElem.css('margin-top', '');
	//update the gui
	this.timeAxis.clear();
	if (entries.length > 1) {
		var entry = entries[0];
		var lastdate = new Date(entry.timestamp);
		var lastStart = entry.GUIElem.offset().top;
		var lastEnd = entry.GUIElem.offset().top + entry.GUIElem.outerHeight();
		for (var i = 1; i < entries.length; i++) {
			entry = entries[i];
			var curdate = new Date(entry.timestamp);

			var ofs = lastStart + 20;
			if (curdate.toDateString() != lastdate.toDateString()) {
				//console.log(curdate.toDateString() + " x " + lastdate.toDateString());
				var ofs = lastEnd + 10;
			}
			
			var dif = ofs - entry.GUIElem.offset().top;
			if (dif > 0)
				entry.GUIElem.css('margin-top', dif+'px');
			
			lastdate = curdate;
			lastStart = entry.GUIElem.offset().top;
			lastEnd = Math.max(lastEnd, entry.GUIElem.offset().top + entry.GUIElem.outerHeight()); 
		}
	}
};

GUITimelineSet.prototype.createTimelineElement = function(parent) {
	var ret = $('<div class="timeline panel panel-primary"></div>');
	parent.append(ret);
	return ret;
};

//==================================================================================

var GUITimeline = function(parentSet, uri, element) {
	this.parentSet = parentSet;
	this.uri = uri;
	this.el = element;
	this.entries = [];
	
	var client = this;
	TA.client.getObject(this.uri).then(function(timeline) {
		//init the title etc
		client.initTimeline(timeline);
		//load and add the entries
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
	this.entries.push(entry);
	
	//create the DOM representation of the entry
	var date = new Date(entry.timestamp);
	var elem = $('<div class="entry"><div>').attr('resource', entry.URI);
	elem.append($('<div class="timestamp"></div>').text(date.toLocaleString()));
	this.el.append(elem);
	entry.GUIElem = elem;
	
	//load and represent the contents
	var self = this;
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
		self.parentSet.recomputeOffsets();
	});
	
};

//==================================================================================

var GUITimeAxis = function(parentElement) {
	this.blocks = {};
	this.entries = [];
	
	this.el = $('<div class="col-md-1"></div>');
	parentElement.append(this.el);
};

GUITimeAxis.prototype.clear = function() {
	this.offsets = [];
	this.entries = [];
	this.el.empty();
};

