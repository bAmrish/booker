package booker

import com.rabbitmq.client.ConnectionFactory

class BootStrap {

    def mqService
    def userMessengerService

    def init = { servletContext ->
        mqService.start()
        def exchange = userMessengerService.createExchange()
    }
    def destroy = {

    }
}
