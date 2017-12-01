package booker

import grails.converters.JSON
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import io.swagger.annotations.Example
import io.swagger.annotations.ExampleProperty

@Api(tags = "UserMessage", description = "APIs for User Message Operation")
class UserMessageController {

    def userMessengerService

    @ApiOperation(
            value = 'Send Message to User',
            nickname = 'user/message/',
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
            @ApiImplicitParam(name = "message",
                    paramType = "body",
                    required = true,
                    value = "Message to be posted",
                    dataType = "JSON",
                    examples = @Example(@ExampleProperty(value = """
                        {\n\tuser: "amrish",\n\tmessage: " Happy Birthday"\n}
                     """))
            )
    ])
    def sendMessage() {
        String message = request.JSON.message
        String user = request.JSON.user
        String messageId = userMessengerService.sendMessageToUser(message, user)
        render (["id": messageId]) as JSON
    }

    @ApiOperation(
            value = 'Get Messages for User',
            nickname = 'user/{user}/messages/',
            produces = 'application/json',
            consumes = 'application/json',
            httpMethod = 'GET'
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
            @ApiImplicitParam(name = "user",
                    paramType = "path",
                    required = true,
                    value = "Message to be posted",
                    dataType = "String"
            )
    ])
    def getMessage() {
        String user = params.user
        List<String> messages = userMessengerService.getMessages(user)
        render ([messages: messages]) as JSON
    }

}
