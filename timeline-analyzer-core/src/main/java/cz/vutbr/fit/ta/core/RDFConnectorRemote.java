package cz.vutbr.fit.ta.core;

import java.io.IOException;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.query.GraphQuery;
import org.eclipse.rdf4j.query.GraphQueryResult;
import org.eclipse.rdf4j.query.MalformedQueryException;
import org.eclipse.rdf4j.query.QueryEvaluationException;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;

/**
 * A base implementation of a connector for a remote RDF repository with the RDF4J API (RDF4J, Blazegraph, ...)
 * 
 * @author burgetr
 */
public class RDFConnectorRemote extends RDFConnectorBase implements RDFConnector
{
	protected String endpointUrl;
	protected RepositoryConnection connection;
	protected Repository repo;
	protected ValueFactory vf;
	
	/**
	 * Establishes a connection to the SPARQL endpoint.
	 * @param endpoint the SPARQL endpoint URL
	 * @throws RepositoryException
	 */
	public RDFConnectorRemote(String endpoint) throws RepositoryException 
	{
	    super();
		endpointUrl = endpoint;
		connection = null;
		vf = SimpleValueFactory.getInstance();
		initRepository();
	}
	
    /**
     * Obtains current connection to the repository or opens a new one when no connection
     * has been opened.
     * @return the connection object
     * @throws RepositoryException 
     */
    public RepositoryConnection getConnection() throws RepositoryException 
    {
        if (connection == null)
            connection = repo.getConnection();
        return connection;
    }

    /**
     * Closes the current connection.
     * @throws RepositoryException
     */
    @Override
    public void close() throws RepositoryException
    {
        if (connection != null)
            connection.close();
        connection = null;
    }
    
    /**
     * Creates a new connection.
     * @throws RepositoryException
     * @throws RepositoryConfigException 
     */
    protected void initRepository() throws RepositoryException
    {
        repo = new SPARQLRepository(endpointUrl);
        repo.initialize();
    }
    
    @Override
	public void add(Resource s, IRI p, Value o) throws IOException
	{
		Statement stmt = vf.createStatement(s, p, o);
		this.connection.add(stmt);
		this.connection.commit();
	}

    @Override
    public void add(Resource s, IRI p, Value o, Resource context) throws IOException
    {
        Statement stmt = vf.createStatement(s, p, o);
        this.connection.add(stmt, context);
        this.connection.commit();
    }

    @Override
	public void add(Model m) throws IOException
	{
        this.connection.begin();
	    this.connection.add(m);
	    this.connection.commit();
	}
	
    @Override
    public void add(Model m, Resource context) throws IOException
    {
        this.connection.begin();
        this.connection.add(m, context);
        this.connection.commit();
    }
    
    public void add(GraphQueryResult m, Resource context)
    {
        this.connection.begin();
        this.connection.add(m, context);
        this.connection.commit();
    }
    
    public void remove(GraphQueryResult statements)
    {
        try {
            this.connection.begin();
            this.connection.remove(statements);
            this.connection.commit();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }
    
    public void remove(Model statements)
    {
        try {
            this.connection.begin();
            this.connection.remove(statements);
            this.connection.commit();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }
    
	/**
	 * Executes a SPARQL SELECT query and returns the result.
	 * @param queryString
	 * @return
	 * @throws RepositoryException
	 * @throws MalformedQueryException
	 * @throws QueryEvaluationException 
	 */
	public TupleQueryResult executeQuery(String queryString) throws RepositoryException, MalformedQueryException, QueryEvaluationException 
	{
	    String qs = getPrefixString() + queryString;
		TupleQuery query = this.connection.prepareTupleQuery(QueryLanguage.SPARQL, qs);
		TupleQueryResult tqr = query.evaluate();
    	return tqr;
	}
	
    /**
     * Executes a SPARQL CONSTRUCT query and returns the graph result.
     * @param queryString
     * @return
     * @throws RepositoryException
     * @throws MalformedQueryException
     * @throws QueryEvaluationException 
     */
    public GraphQueryResult executeConstructQuery(String queryString) throws RepositoryException, MalformedQueryException, QueryEvaluationException 
    {
        String qs = getPrefixString() + queryString;
        GraphQuery query = this.connection.prepareGraphQuery(QueryLanguage.SPARQL, qs);
        GraphQueryResult tqr = query.evaluate();
        return tqr;
    }
    
}
