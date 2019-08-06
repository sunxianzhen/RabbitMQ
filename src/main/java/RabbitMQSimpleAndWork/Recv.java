package RabbitMQSimpleAndWork;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import util.ConnectionUtil;

public class Recv {
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(Send.QUEUE_NAME, false, false, false, null);
        //定义队列消费者，构造器写入通道，关联某个通道
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //这里是进行手动回复收到消息的确信信息的前提设置，必须得把它设置成同一时刻只发送一条消息。
        channel.basicQos(1);
        //通道接收消息，第一个参数队列名，第二个参数就是是否是自动确认，false代表手动确认，第三个参数传入消费者对象。
        channel.basicConsume(Send.QUEUE_NAME, false, consumer);
        while (true) {
            //这是个阻塞式方法。获取一条消息
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.err.println(msg);
            //手动发送确认包，标记此条消息已经消费。
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
}
