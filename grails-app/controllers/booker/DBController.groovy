package booker

import grails.converters.JSON
import io.swagger.annotations.*

@Api(tags = ["DB"], description = "APIs for Database Operation")
class DBController {

    def couchDbService

    @ApiOperation(
            value = "List all the databases",
            nickname = "dbs/",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "GET",
            response = String,
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
    def index() {
        render couchDbService.getAllDBs() as JSON
    }

    @ApiOperation(
            value = 'Create a database',
            nickname = 'db/{dbName}',
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
            @ApiImplicitParam(name = "dbName",
                    paramType = "path",
                    required = true,
                    value = "Database Name",
                    dataType = "string"
            )
    ])
    def create() {
        String dbName = params.dbName
        render couchDbService.createDB(dbName) as JSON
    }

    @ApiOperation(
            value = 'Delete a database',
            nickname = 'db/{dbName}',
            produces = 'application/json',
            consumes = 'application/json',
            httpMethod = 'DELETE'
    )
    @ApiResponses([
            @ApiResponse(code = 405,
                    message = 'Method Not Allowed. Only DELETE is allowed'
            ),

            @ApiResponse(code = 404,
                    message = 'Method Not Found'
            )
    ])
    @ApiImplicitParams([
            @ApiImplicitParam(name = "dbName",
                    paramType = "path",
                    required = true,
                    value = "Database Name",
                    dataType = "string"
            )
    ])
    def delete() {
        String dbName = params.dbName
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
