package booker

import com.rabbitmq.client.AMQP.Exchange.DeclareOk
import com.rabbitmq.client.Channel
import grails.transaction.Transactional

@Transactional
class UserMessengerService {

    def mqService

    private static final String USER_INBOX_EXCHANGE_NAME = "user-inboxes"

    def createExchange() {

        mqService.call(new ChannelCallable() {
            @Override
            String getDescription() {
                "Declaring direct exchange: $USER_INBOX_EXCHANGE_NAME"
            }

            @Override
            DeclareOk call(Channel channel) throws IOException {
                String exchangeName = USER_INBOX_EXCHANGE_NAME
                String type = "direct"
                boolean durable = true
                boolean autoDelete = false
                Map<String, Object> arguments = null

                return channel.exchangeDeclare(exchangeName, type, durable, autoDelete, arguments)
            }
        })
    }
}
