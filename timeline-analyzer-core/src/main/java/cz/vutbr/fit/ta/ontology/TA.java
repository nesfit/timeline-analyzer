package cz.vutbr.fit.ta.ontology;

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
	 * {@code http://nesfit.github.io/ontology/ta.owl#Content}.
	 * <p>
	 * A particular content of an entry.
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#Content">Content</a>
	 */
	public static final IRI Content;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#Entry}.
	 * <p>
	 * An entry in the timeline
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#Entry">Entry</a>
	 */
	public static final IRI Entry;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#Image}.
	 * <p>
	 * An image contained in an entry.
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#Image">Image</a>
	 */
	public static final IRI Image;

	/**
	 * {@code http://nesfit.github.io/ontology/ta.owl#sourceTimeline}.
	 *
	 * @see <a href="http://nesfit.github.io/ontology/ta.owl#sourceTimeline">sourceTimeline</a>
	 */
	public static final IRI sourceTimeline;

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

	static {
		ValueFactory factory = SimpleValueFactory.getInstance();

		Content = factory.createIRI(TA.NAMESPACE, "Content");
		Entry = factory.createIRI(TA.NAMESPACE, "Entry");
		Image = factory.createIRI(TA.NAMESPACE, "Image");
		sourceTimeline = factory.createIRI(TA.NAMESPACE, "sourceTimeline");
		TextContent = factory.createIRI(TA.NAMESPACE, "TextContent");
		Timeline = factory.createIRI(TA.NAMESPACE, "Timeline");
	}

	private TA() {
		//static access only
	}

}
