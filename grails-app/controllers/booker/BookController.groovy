package booker

import grails.converters.JSON
import io.swagger.annotations.*

@Api(tags = ["Book"], description = "APIs for Book Operation")
class BookController {
    def bookService

    def index() {
        render "books aglore!"
    }

    @ApiOperation(
            value = 'Add a new Book',
            nickname = 'book',
            produces = 'application/json',
            consumes = 'application/json',
            httpMethod = 'POST'
    )
    @ApiResponses([
            @ApiResponse(code = 405,
                    message = 'Method Not Allowed. Only POST is allowed'
            ),

            @ApiResponse(code = 404,
                    message = 'Method Not Found'
            )
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(name = "book",
                    paramType = "body",
                    required = true,
                    value = "Book Json to be posted",
                    dataType = "JSON",
                    examples = @Example(@ExampleProperty(value = """
                    {\n\ttitle: "The Old Man and the Sea",\n\tauthor: " Ernest Hemingway",\n\tratings: 5\n}
                    """))
            )
    ])
    def create(){
        Map book = request.getJSON()
        Book newBook = new Book (book)
        Map bookMap = bookService.create(newBook)
        render bookMap as JSON
    }

    @ApiOperation(
            value = "List all the books",
            nickname = "books/",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "GET",
            response = Book,
            responseContainer = "List"
    )
    @ApiResponses([
            @ApiResponse(code = 405,
                    message = "Method Not Allowed. Only GET is allowed"
            ),

            @ApiResponse(code = 404,
                    message = "Method Not Found"
            )
    ])
    def list() {
        List<Book> books = bookService.getAllBooks()
        render books as JSON
    }

    @ApiOperation(
            value = "Get details for a single book",
            nickname = "book/{id}",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "GET",
            response = Book
    )
    @ApiResponses([
            @ApiResponse(code = 405,
                    message = "Method Not Allowed. Only GET is allowed"
            ),

            @ApiResponse(code = 404,
                    message = "Method Not Found"
            )
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(name = "id",
                    paramType = "path",
                    required = true,
                    value = "Unique id of that book",
                    dataType = "string"
            )
    ])
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

    @ApiOperation(
            value = "Delete a book by its id",
            nickname = "book/{id}",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "DELETE",
            response = String
    )
    @ApiResponses([
            @ApiResponse(code = 405,
                    message = "Method Not Allowed. Only GET is allowed"
            ),

            @ApiResponse(code = 404,
                    message = "Method Not Found"
            )
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(name = "id",
                    paramType = "path",
                    required = true,
                    value = "Unique id of that book",
                    dataType = "string"
            )
    ])
    def delete() {
        String bookId = params.id
        Map bookMap = bookService.delete(bookId)
        render bookMap as JSON
    }
}

