import booker.RabbitMQService
import com.rabbitmq.client.ConnectionFactory

// Place your Spring DSL code here
beans = {
    ConnectionFactory factory = new ConnectionFactory()
    factory.username = "ccm-dev"
    factory.password = "coney123"
    factory.virtualHost = "ccm-dev-vhost"
    factory.host = "localhost"
    factory.port = 5672

    mqService(RabbitMQService, factory)
}
