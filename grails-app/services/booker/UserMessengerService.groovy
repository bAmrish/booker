package booker

import com.rabbitmq.client.AMQP.BasicProperties
import com.rabbitmq.client.AMQP.Queue.BindOk
import com.rabbitmq.client.AMQP.Exchange.DeclareOk
import com.rabbitmq.client.Channel
import com.rabbitmq.client.GetResponse
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

    def login(String username){
        setupUserInbox(username)
    }

    private void setupUserInbox(String username){
        String queueName = getUserInboxQueueName(username)

        mqService.call(new ChannelCallable<BindOk>() {
            @Override
            String getDescription() {
                return """Binding queue: $queueName to exchange: $USER_INBOX_EXCHANGE_NAME."""
            }

            @Override
            BindOk call(Channel channel) throws IOException {
                return setupUserInboxQueue(queueName, channel)
            }
        })
    }

    private BindOk setupUserInboxQueue(String queueName, Channel channel){
        String exchangeName = USER_INBOX_EXCHANGE_NAME
        String routingKey = queueName
        boolean durable = true
        boolean autoDelete = false
        boolean exclusive = false
        Map<String, Object> arguments = null

        channel.queueDeclare(queueName, durable, exclusive, autoDelete, arguments)

        return channel.queueBind(queueName, exchangeName, routingKey)
    }

    private String getUserInboxQueueName(String username){
        if(!username || username.trim().equals('')){
            return null
        }

        return """user-inbox-$username"""
    }

    def sendMessageToUser(String message, String user){
        String queueName = getUserInboxQueueName(user)

        mqService.call(new ChannelCallable() {
            @Override
            String getDescription() {
                return """Sending message "$message" to $user."""
            }

            @Override
            String call(Channel channel) throws IOException {

                String contentType = 'application/json'
                String contentEncoding = 'UTF-8'
                String messageId = UUID.randomUUID().toString()
                int deliveryMode = 2 //persistent

                String routingKey = queueName
                String exchange = USER_INBOX_EXCHANGE_NAME

                BasicProperties properties = new BasicProperties().builder()
                    .contentType(contentType)
                    .contentEncoding(contentEncoding)
                    .messageId(messageId)
                    .deliveryMode(deliveryMode)
                    .build()

                setupUserInboxQueue(queueName, channel)

                channel.basicPublish(exchange, routingKey, properties, message.getBytes(contentEncoding))

                return messageId
            }
        })

    }

    List<Map<String, Object>> getMessages(String user){
        def queueName = getUserInboxQueueName(user)
        def exchangeName = USER_INBOX_EXCHANGE_NAME

        mqService.call(new ChannelCallable<List<String>>() {
            @Override
            String getDescription() {
                return "Fetching messages for $user."
            }

            @Override
            List<Map<String, Object>> call(Channel channel) throws IOException {
                List<Map<String, Object>> messages = []
                GetResponse response =  channel.basicGet(queueName, true)
                while(response != null){
                    Map message = [:]
                    message.id = response.props.messageId
                    message.content =  new String(response.body, response.props.contentEncoding)
                    messages << message
                    response =  channel.basicGet(queueName, true)
                }
                return messages
            }
        })
    }
}
