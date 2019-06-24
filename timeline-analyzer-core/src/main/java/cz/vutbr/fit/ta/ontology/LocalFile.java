package cz.vutbr.fit.ta.ontology;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import com.github.radkovo.rdf4j.builder.EntityFactory;
import com.github.radkovo.rdf4j.builder.TargetModel;
import cz.vutbr.fit.ta.ontology.vocabulary.TA;

/**
 * A investigated file on a local filesystem.
 * <p>
 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#LocalFile>}
 */
public class LocalFile extends Object
{
	public static final IRI CLASS_IRI = vf.createIRI("http://nesfit.github.io/ontology/ta.owl#LocalFile");

	/**
	 * A name of a file.
	 * <p>
	 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#fileName>}
	 */
	private String fileName;

	/**
	 * A file path in a local filesystem.
	 * <p>
	 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#path>}
	 */
	private String path;


	public LocalFile(IRI iri) {
		super(iri);
	}

	@Override
	public IRI getClassIRI() {
		return LocalFile.CLASS_IRI;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public void addToModel(TargetModel target) {
		super.addToModel(target);
		addValue(target, TA.fileName, fileName);
		addValue(target, TA.path, path);
	}

	@Override
	public void loadFromModel(Model model, EntityFactory efactory) {
		super.loadFromModel(model, efactory);
		final Model m = model.filter(getIRI(), null, null);
		fileName = loadStringValue(m, TA.fileName);
		path = loadStringValue(m, TA.path);
	}
}
