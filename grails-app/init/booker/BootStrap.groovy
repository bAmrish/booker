package booker

import com.rabbitmq.client.ConnectionFactory

class BootStrap {

    def mqService

    def init = { servletContext ->
        mqService.start()
    }
    def destroy = {

    }
}
