package booker

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.ShutdownListener
import com.rabbitmq.client.ShutdownSignalException
import grails.transaction.Transactional

import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

@Transactional
class RabbitMQService implements ShutdownListener{

    private final ConnectionFactory factory
    private volatile Connection connection
    private final ScheduledExecutorService executorService

    RabbitMQService(ConnectionFactory factory) {
        this.factory = factory
        this.connection = null
    }

    void start(){
        try {
            connection = factory.newConnection()
            connection.addShutdownListener(this)
            log.info """Connected to RabbitMQ server @ $factory.host:$factory.port."""
        } catch (Exception e) {
            log.error """Failed to connect to RabbitMQ server @ $factory.host:$factory.port.""", e
            asyncWaitAndReconnect()
        }
    }

    private void asyncWaitAndReconnect(){
        executorService.schedule(new Runnable() {
            @Override
            void run() {
                start()
            }
        }, 15, TimeUnit.SECONDS)
    }

    @Override
    void shutdownCompleted(ShutdownSignalException cause) {
        if(!cause.isInitiatedByApplication()){
            log.error """Connection lost to RabbitMQ server @ $factory.host:$factory.port.""", cause
            log.info "Attempting to reconnect to RabbitMQ server."
            asyncWaitAndReconnect()
        }
    }

    void stop(){
        executorService.shutdown()

        if(connection == null){
            return
        }

        try {
            connection.close()
        } catch (Exception e) {
            log.error "Failed to close connection to RabbitMQ server", e
        } finally {
            connection = null
        }

    }

    Channel createChannel(){
        if(connection !== null){
            try {
                connection.createChannel()
            } catch (Exception e){
                log.error """Failed to create channel.""", e
            }
        }

        return null;
    }

    void closeChannel(Channel channel){
        if(channel != null && !channel.isOpen()){
            try{
                channel.close()
            } catch (Exception e) {
                log.error """Failed to close the channel $channel""", e
            }

        }else {
            log.error """Failed to close the channel $channel"""
        }
    }
}

