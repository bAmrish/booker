package booker

import grails.converters.JSON
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

@Api(tags = "User", description = "APIs for User Operation")
class UserController {

    def userMessengerService

    @ApiOperation(
            value = 'Login an user',
            nickname = 'user/login/{username}',
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
            @ApiImplicitParam(name = "username",
                    paramType = "path",
                    required = true,
                    value = "Username",
                    dataType = "string"
            )
    ])
    def login() {
        String username = params.username
        userMessengerService.login(username)
        render (["message": "ok"]) as JSON
    }
}
