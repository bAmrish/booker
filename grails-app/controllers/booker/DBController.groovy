package booker

import grails.converters.JSON

class DBController {

    def couchDbService

    def index() {
        render couchDbService.getAllDBs() as JSON
    }

    def create() {
        String dbName = params.id
        render couchDbService.createDB(dbName) as JSON
    }

    def delete() {
        String dbName = params.id
        render couchDbService.deleteDB(dbName) as JSON
    }

    def add() {

        def book = [
                title: "To Kill A Mocking Bird",
                author: "Harper Lee"
        ]
        render couchDbService.createDocument(book, "books") as JSON
    }

    def list(){
        String dbName = params.id
        render couchDbService.getAllDocuments(dbName) as JSON
    }

}
