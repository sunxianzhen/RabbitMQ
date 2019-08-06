package RabbitMQFanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import util.ConnectionUtil;

public class Recv {
    public final static String QUEUE_NAME = "secend_queue";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //这里是把声明的队列绑定到某个路由器上，如果路由器在没有队列绑定的情况下传送了消息，那么消息就会丢失。
        //最后一个null参数是路由规则，就是发布的时候设定的规则。fanout没有路有规则，所以null
        channel.queueBind(QUEUE_NAME, Send.EXCHANGE_NAME, null);
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
