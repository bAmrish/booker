package booker

import grails.transaction.Transactional

@Transactional
class BookService {
    def couchDbService
    private final String booksDBName = "books"

    Map create(Book book) {
        Map bookMap = book.asMap()
        Map response = couchDbService.createDocument(bookMap, booksDBName)
        return response
    }

    List<Book> getAllBooks() {
        Map response = couchDbService.getAllDocuments(booksDBName)
        List<Book> books = []
        response.rows.each { book ->
            books << dbObject2Book(book.doc)
        }
        return books
    }

    Book get(String id){
        Map response = couchDbService.getDocument(id, booksDBName)
        return  dbObject2Book(response)
    }

    Map delete(String id){
        Book book = get(id)
        Map response = couchDbService.deleteDocument(id, book.rev, booksDBName)
        return response
    }

    private Book dbObject2Book(Map db){
        Book book = new Book(db)
        book.rev = db._rev
        book.id = db._id
        return book
    }
}
