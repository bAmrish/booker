package booker

import grails.converters.JSON

class BookController {
    def bookService

    def index() {
        render "books aglore!"
    }

    def create(){
        Map book = request.getJSON()
        Book newBook = new Book (book)
        Map bookMap = bookService.create(newBook)
        render bookMap as JSON
    }

    def list() {
        List<Book> books = bookService.getAllBooks()
        render books as JSON
    }

    def get() {
        render bookService.get(params.id) as JSON
    }

    def update() {
        String id = params.id
        Map book = request.getJSON()
        Book updatedBook = new Book(book)
        Map response = bookService.update(id, updatedBook)
        render response as JSON
    }

    def delete() {
        String bookId = params.id
        Map bookMap = bookService.delete(bookId)
        render bookMap as JSON
    }
}

