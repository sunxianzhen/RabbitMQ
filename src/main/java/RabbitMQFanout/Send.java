package RabbitMQFanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.ConnectionUtil;

public class Send {
    public final static String EXCHANGE_NAME = "secend";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //订阅模式下不能直接传送到通道，所以需要定义路由器，通过路由器进行对所有绑定通道的消息转发。
        // 第一个参数是路由器名称，第二个是路由模式，fanout为广播模式，即订阅模式。
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String msg = "i am Sender for Fanout model";
        //这里就有了第一个参数，所以第二个应该写路由规则，但是fanout没有规则，所有为空
        channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
        channel.close();
        connection.close();
    }

}
