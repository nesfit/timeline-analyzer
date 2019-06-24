package cz.vutbr.fit.ta.ontology.vocabulary;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;

/**
 * Timeline Analyzer Ontology.
 * <p>
 * An ontology used for representing the social network timelines and
 * their contents..
 * <p>
 * Namespace TA.
 * Prefix: {@code <http://nesfit.github.io/ontology/ta.owl#>}
 */
public class TA {

	/** {@code http://nesfit.github.io/ontology/ta.owl#} **/
	public static final String NAMESPACE = "http://nesfit.github.io/ontology/ta.owl#";

	/** {@code ta} **/
	public static final String PREFIX = "ta";

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#contains}.
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#contains">contains</a>
	 */
	public static final IRI contains;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#Content}.
	 * <p>
	 * A particular content of an entry.
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#Content">Content</a>
	 */
	public static final IRI Content;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#contextLink}.
	 * <p>
	 * A generic link between two profile entities (e.g. profiles, entries,
	 * contents).
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#contextLink">contextLink</a>
	 */
	public static final IRI contextLink;

	/**
	 * created
	 * <p>
	 * {@code http://nesfit.github.io/ontology/ta.owl#CreationEvent}.
	 * <p>
	 * An event of the object creation
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#CreationEvent">CreationEvent</a>
	 */
	public static final IRI CreationEvent;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#Entry}.
	 * <p>
	 * An entry in the timeline
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#Entry">Entry</a>
	 */
	public static final IRI Entry;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#Event}.
	 * <p>
	 * An event with a subject and time assigned
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#Event">Event</a>
	 */
	public static final IRI Event;

	/**
	 * downloaded
	 * <p>
	 * {@code http://nesfit.github.io/ontology/ta.owl#FileDownloadEvent}.
	 * <p>
	 * An event of the file download from a URL
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#FileDownloadEvent">FileDownloadEvent</a>
	 */
	public static final IRI FileDownloadEvent;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#fileName}.
	 * <p>
	 * A name of a file
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#fileName">fileName</a>
	 */
	public static final IRI fileName;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#GeoContent}.
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#GeoContent">GeoContent</a>
	 */
	public static final IRI GeoContent;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#Image}.
	 * <p>
	 * An image contained in an entry.
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#Image">Image</a>
	 */
	public static final IRI Image;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#latitude}.
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#latitude">latitude</a>
	 */
	public static final IRI latitude;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#linksResource}.
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#linksResource">linksResource</a>
	 */
	public static final IRI linksResource;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#LocalFile}.
	 * <p>
	 * A investigated file on a local filesystem
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#LocalFile">LocalFile</a>
	 */
	public static final IRI LocalFile;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#longitude}.
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#longitude">longitude</a>
	 */
	public static final IRI longitude;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#Object}.
	 * <p>
	 * A generic investigated object
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#Object">Object</a>
	 */
	public static final IRI Object;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#path}.
	 * <p>
	 * A file path in a local filesystem
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#path">path</a>
	 */
	public static final IRI path;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#refersTo}.
	 * <p>
	 * Assigns an object to an event
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#refersTo">refersTo</a>
	 */
	public static final IRI refersTo;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#resourceTitle}.
	 * <p>
	 * A title of a web resource (web page title, if present)
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#resourceTitle">resourceTitle</a>
	 */
	public static final IRI resourceTitle;

	/**
	 * URL also published in
	 * <p>
	 * {@code http://nesfit.github.io/ontology/ta.owl#sameURL}.
	 * <p>
	 * Indicates that the URL has been also published in an entry in another
	 * profile
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#sameURL">sameURL</a>
	 */
	public static final IRI sameURL;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#SocialNetworkObject}.
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#SocialNetworkObject">SocialNetworkObject</a>
	 */
	public static final IRI SocialNetworkObject;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#sourceId}.
	 * <p>
	 * An identifier of the entry in the source timeline (e.g. Twitter id)
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#sourceId">sourceId</a>
	 */
	public static final IRI sourceId;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#sourceTimeline}.
	 * <p>
	 * Assigns a source timeline to an event
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#sourceTimeline">sourceTimeline</a>
	 */
	public static final IRI sourceTimeline;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#sourceUrl}.
	 * <p>
	 * Source URL of the media.
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#sourceUrl">sourceUrl</a>
	 */
	public static final IRI sourceUrl;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#tags}.
	 * <p>
	 * Tags specifying the properties of the entry (e.g. 'download', 'visit',
	 * etc.)
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#tags">tags</a>
	 */
	public static final IRI tags;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#text}.
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#text">text</a>
	 */
	public static final IRI text;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#TextContent}.
	 * <p>
	 * Text contained in an entry.
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#TextContent">TextContent</a>
	 */
	public static final IRI TextContent;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#Timeline}.
	 * <p>
	 * A sequence of entries displayed in a signle time line.
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#Timeline">Timeline</a>
	 */
	public static final IRI Timeline;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#timestamp}.
	 * <p>
	 * A timestamp for an event
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#timestamp">timestamp</a>
	 */
	public static final IRI timestamp;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#URLContent}.
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#URLContent">URLContent</a>
	 */
	public static final IRI URLContent;

	/**
	 * Source URL mentioned in
	 * <p>
	 * {@code http://nesfit.github.io/ontology/ta.owl#urlMention}.
	 * <p>
	 * Indicates that the URL of a content (image, other URL, etc.) is
	 * mentioned in another entry.
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#urlMention">urlMention</a>
	 */
	public static final IRI urlMention;

	/**
	 * visited URL
	 * <p>
	 * {@code http://nesfit.github.io/ontology/ta.owl#URLVisitEvent}.
	 * <p>
	 * An event of a URL visit
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#URLVisitEvent">URLVisitEvent</a>
	 */
	public static final IRI URLVisitEvent;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#WebResource}.
	 * <p>
	 * A web resource with a URL
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#WebResource">WebResource</a>
	 */
	public static final IRI WebResource;

	static {
		ValueFactory factory = SimpleValueFactory.getInstance();

		contains = factory.createIRI(TA.NAMESPACE, "contains");
		Content = factory.createIRI(TA.NAMESPACE, "Content");
		contextLink = factory.createIRI(TA.NAMESPACE, "contextLink");
		CreationEvent = factory.createIRI(TA.NAMESPACE, "CreationEvent");
		Entry = factory.createIRI(TA.NAMESPACE, "Entry");
		Event = factory.createIRI(TA.NAMESPACE, "Event");
		FileDownloadEvent = factory.createIRI(TA.NAMESPACE, "FileDownloadEvent");
		fileName = factory.createIRI(TA.NAMESPACE, "fileName");
		GeoContent = factory.createIRI(TA.NAMESPACE, "GeoContent");
		Image = factory.createIRI(TA.NAMESPACE, "Image");
		latitude = factory.createIRI(TA.NAMESPACE, "latitude");
		linksResource = factory.createIRI(TA.NAMESPACE, "linksResource");
		LocalFile = factory.createIRI(TA.NAMESPACE, "LocalFile");
		longitude = factory.createIRI(TA.NAMESPACE, "longitude");
		Object = factory.createIRI(TA.NAMESPACE, "Object");
		path = factory.createIRI(TA.NAMESPACE, "path");
		refersTo = factory.createIRI(TA.NAMESPACE, "refersTo");
		resourceTitle = factory.createIRI(TA.NAMESPACE, "resourceTitle");
		sameURL = factory.createIRI(TA.NAMESPACE, "sameURL");
		SocialNetworkObject = factory.createIRI(TA.NAMESPACE, "SocialNetworkObject");
		sourceId = factory.createIRI(TA.NAMESPACE, "sourceId");
		sourceTimeline = factory.createIRI(TA.NAMESPACE, "sourceTimeline");
		sourceUrl = factory.createIRI(TA.NAMESPACE, "sourceUrl");
		tags = factory.createIRI(TA.NAMESPACE, "tags");
		text = factory.createIRI(TA.NAMESPACE, "text");
		TextContent = factory.createIRI(TA.NAMESPACE, "TextContent");
		Timeline = factory.createIRI(TA.NAMESPACE, "Timeline");
		timestamp = factory.createIRI(TA.NAMESPACE, "timestamp");
		URLContent = factory.createIRI(TA.NAMESPACE, "URLContent");
		urlMention = factory.createIRI(TA.NAMESPACE, "urlMention");
		URLVisitEvent = factory.createIRI(TA.NAMESPACE, "URLVisitEvent");
		WebResource = factory.createIRI(TA.NAMESPACE, "WebResource");
	}

	private TA() {
		//static access only
	}

}
