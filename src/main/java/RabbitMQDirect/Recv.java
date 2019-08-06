package RabbitMQDirect;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import util.ConnectionUtil;

public class Recv {
    public final static String QUEUE_NAME = "third_queue";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //注意这里绑定的时候最后一个参数写了路由规则，他和发布的时候写的路由规则是一样的key，这样那个路由器就会知道往这个通道里发消息。而不给他人发
        channel.queueBind(QUEUE_NAME, Send.EXCHANGE_NAME, "sxz");
        channel.basicQos(1);
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, false, consumer);
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            System.err.println(new String(delivery.getBody()));
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
}
