package RabbitMQSimpleAndWork;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.ConnectionUtil;
public class Send {
    public final static String QUEUE_NAME = "First";

    public static void main(String[] args) throws Exception {
        //获取链接
        Connection connection = ConnectionUtil.getConnection();
        //获取网络通道
        Channel channel = connection.createChannel();
        //声明一个队列，五个参数 队列名、是否持久化存储、是否进行排他设置、是否自动删除、一些属性。
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String msg = "i am sender";
        //通过通道把信息发布出去，此方法第一个参数指的是路由器的名称，第二个参数指的是路由器的路由规则，fanout中，这个参数写空。但是如果第一个参数
        //为""，则第二个参数变成了队列的名称，代表要发布到哪个队列。 第三个参数一些配置性参数，第四个就是消息的byte值
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        channel.close();
        connection.close();
    }
}
