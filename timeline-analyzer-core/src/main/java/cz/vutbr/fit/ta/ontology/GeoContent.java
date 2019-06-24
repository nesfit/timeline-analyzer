package cz.vutbr.fit.ta.ontology;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import com.github.radkovo.rdf4j.builder.EntityFactory;
import com.github.radkovo.rdf4j.builder.TargetModel;
import cz.vutbr.fit.ta.ontology.vocabulary.TA;

/**
 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#GeoContent>}
 */
public class GeoContent extends Content
{
	public static final IRI CLASS_IRI = vf.createIRI("http://nesfit.github.io/ontology/ta.owl#GeoContent");

	/**
	 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#latitude>}
	 */
	private double latitude;

	/**
	 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#longitude>}
	 */
	private double longitude;


	public GeoContent(IRI iri) {
		super(iri);
	}

	@Override
	public IRI getClassIRI() {
		return GeoContent.CLASS_IRI;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public void addToModel(TargetModel target) {
		super.addToModel(target);
		addValue(target, TA.latitude, latitude);
		addValue(target, TA.longitude, longitude);
	}

	@Override
	public void loadFromModel(Model model, EntityFactory efactory) {
		super.loadFromModel(model, efactory);
		final Model m = model.filter(getIRI(), null, null);
		latitude = loadDoubleValue(m, TA.latitude);
		longitude = loadDoubleValue(m, TA.longitude);
	}
}
