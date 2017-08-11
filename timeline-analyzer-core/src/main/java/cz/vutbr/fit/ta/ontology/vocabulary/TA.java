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
	 * {@code http://nesfit.github.io/ontology/ta.owl#Entry}.
	 * <p>
	 * An entry in the timeline
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#Entry">Entry</a>
	 */
	public static final IRI Entry;

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
	 * {@code http://nesfit.github.io/ontology/ta.owl#longitude}.
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#longitude">longitude</a>
	 */
	public static final IRI longitude;

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
	 * {@code http://nesfit.github.io/ontology/ta.owl#sourceId}.
	 * <p>
	 * An identifier of the entry in the source timeline (e.g. Twitter id)
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#sourceId">sourceId</a>
	 */
	public static final IRI sourceId;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#sourceTimeline}.
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
	 * Entry creation timestamp
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

	static {
		ValueFactory factory = SimpleValueFactory.getInstance();

		contains = factory.createIRI(TA.NAMESPACE, "contains");
		Content = factory.createIRI(TA.NAMESPACE, "Content");
		contextLink = factory.createIRI(TA.NAMESPACE, "contextLink");
		Entry = factory.createIRI(TA.NAMESPACE, "Entry");
		GeoContent = factory.createIRI(TA.NAMESPACE, "GeoContent");
		Image = factory.createIRI(TA.NAMESPACE, "Image");
		latitude = factory.createIRI(TA.NAMESPACE, "latitude");
		longitude = factory.createIRI(TA.NAMESPACE, "longitude");
		sameURL = factory.createIRI(TA.NAMESPACE, "sameURL");
		sourceId = factory.createIRI(TA.NAMESPACE, "sourceId");
		sourceTimeline = factory.createIRI(TA.NAMESPACE, "sourceTimeline");
		sourceUrl = factory.createIRI(TA.NAMESPACE, "sourceUrl");
		text = factory.createIRI(TA.NAMESPACE, "text");
		TextContent = factory.createIRI(TA.NAMESPACE, "TextContent");
		Timeline = factory.createIRI(TA.NAMESPACE, "Timeline");
		timestamp = factory.createIRI(TA.NAMESPACE, "timestamp");
		URLContent = factory.createIRI(TA.NAMESPACE, "URLContent");
		urlMention = factory.createIRI(TA.NAMESPACE, "urlMention");
	}

	private TA() {
		//static access only
	}

}
