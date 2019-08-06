package RabbitMQTopic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import util.ConnectionUtil;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

public class Recv {
    public final static String QUEUE_NAME = "four_queue";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //这里我用了*进行路由规则匹配。
        channel.queueBind(QUEUE_NAME, Send.EXCHANGE_NAME, "sxz.*");
        channel.basicQos(1);
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, false, consumer);
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            System.err.println(new String(delivery.getBody()));
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(delivery.getBody());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            String msg = (String)objectInputStream.readObject();
            objectInputStream = null;
            byteArrayInputStream = null;
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
}
