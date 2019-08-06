package RabbitMQTopic;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.ConnectionUtil;

import java.io.IOException;

public class Send {
    public final static String EXCHANGE_NAME = "four";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //设定模式为topic，通配符模式，即通过 * 和 # 来进行路由规则匹配。 * 可以匹配一个词，而#可以匹配多个。例如 sxz.sxz和sxz.sxz.sxz
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String msg = "i am Sender for Topic Model";
        channel.basicPublish(EXCHANGE_NAME, "sxz.1", null, msg.getBytes());
        channel.close();
        connection.close();
    }
}
