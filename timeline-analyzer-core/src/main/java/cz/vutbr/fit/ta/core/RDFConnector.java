package cz.vutbr.fit.ta.core;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
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
 * 
 * 
 * @author burgetr
 */
public class RDFConnector
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
	public RDFConnector(String endpoint) throws RepositoryException 
	{
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
    public void closeConnection() throws RepositoryException
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
    
	/**
	 * Adds single tripple to the repository.
	 * @param s
	 * @param p
	 * @param o
	 * @throws RepositoryException
	 */
	public void add(Resource s, IRI p, Value o) 
	{
		try {
			Statement stmt = vf.createStatement(s, p, o);
			this.connection.add(stmt);
			this.connection.commit();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
	}

	public void add(Model m)
	{
        try {
            this.connection.begin();
    	    this.connection.add(m);
    	    this.connection.commit();
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Executes a SPARQL query and returns the result.
	 * @param queryString
	 * @return
	 * @throws RepositoryException
	 * @throws MalformedQueryException
	 * @throws QueryEvaluationException 
	 */
	public TupleQueryResult executeQuery(String queryString) throws RepositoryException, MalformedQueryException, QueryEvaluationException 
	{
		try {
			TupleQuery query = this.connection.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
			TupleQueryResult tqr = query.evaluate();
        	return tqr;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
}
