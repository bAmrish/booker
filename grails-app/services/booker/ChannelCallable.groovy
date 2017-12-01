package booker

import com.rabbitmq.client.Channel

interface ChannelCallable<T> {

    String getDescription()

    T call(Channel channel) throws IOException
}