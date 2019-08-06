package RabbitMQDirect;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.ConnectionUtil;

public class Send {
    public final static String EXCHANGE_NAME = "third";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //这里设定为路由模式。
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        String msg = "i am Sender for Direct Model";
        //路由模式下是根据routekey进行判断往哪个通道里发消息，所以这个第二个参数是有值的。这个值就是路由规则
        channel.basicPublish(EXCHANGE_NAME, "sxz", null, msg.getBytes());
        channel.close();
        connection.close();
    }
}
