
/**
 * Shared configuration for the application.
 */
export class Config {

    static production: any = {
        endpoint: window.location.protocol + '//' + window.location.host + '/rdf4j-server',
        repository: 'ta'
    };

    static devel: any = {
        endpoint: 'http://localhost:8080/rdf4j-server',
        repository: 'testplaso'
    };

}
