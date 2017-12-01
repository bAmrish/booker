package booker

import grails.converters.JSON
import io.swagger.annotations.*

@Api(tags = ["Log"], description = "APIs for testing logging in the applicaiton")
class LogTestingController {

    @ApiOperation(
            value = "Log a message",
            nickname = "log/{message}",
            produces = "application/json",
            consumes = "application/json",
            httpMethod = "POST",
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
            @ApiImplicitParam(name = "message",
                    paramType = "path",
                    required = true,
                    value = "message",
                    dataType = "string"
            )
    ])
    def log() {
        String message = params.message

        log.trace "trace   : $message"
        log.debug "debug   : $message"
        log.info  "info    : $message"
        log.warn  "warn    : $message"
        log.error "error   : $message"

        render (["message": "ok"]) as JSON
    }
}
