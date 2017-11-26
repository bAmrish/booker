package booker

import grails.transaction.Transactional

import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.PUT
import static groovyx.net.http.Method.DELETE
import static groovyx.net.http.ContentType.JSON

@Transactional
class CouchDbService {

    HTTPBuilder http

    CouchDbService(){
        def config = grails.util.Holders.getGrailsApplication().config
        String url = config.getProperty('app.couchdb.url')
        System.out.println("""Couch DB URL: $url""")

        http = new HTTPBuilder(url, 'application/json')
    }


    List getAllDBs() {
        List response = http.get(path: '/_all_dbs')

        println response

        return response
    }

    Map createDB(String dbName){
        Map response = http.request(PUT, JSON) { request ->
            uri.path = "/$dbName"
        }

        println response

        return response

    }

    Map deleteDB(String dbName){
        Map response = http.request(DELETE, JSON) {
            request -> uri.path = "/$dbName"
        }

        println response

        return response
    }

    Map createDocument(Map document, String dbName) {

        Map response = http.post(path: "/$dbName", body: document, requestContentType: JSON)
        println response

        return response
    }

    Map getAllDocuments(String dbName) {

        Map response = http.get(path: "$dbName/_all_docs", query: [include_docs: true])

        println response

        return response
    }


    Map getDocument(String id, String dbName){
        Map response = http.get(path: "$dbName/$id", query: [include_docs: true])

        println response

        return response
    }

    Map updateDocument(String id, Map document, String dbName) {
        Map response = http.request(PUT, JSON) { request ->
            uri.path = "/$dbName/$id"
            body = document
        }

        println response

        return response
    }

    Map deleteDocument(String id, String rev, String dbName) {
        Map response = http.request(DELETE, JSON) { request ->
            uri.path = "/$dbName/$id"
            headers.'If-Match' = rev
        }

        println response

        return response
    }
}
