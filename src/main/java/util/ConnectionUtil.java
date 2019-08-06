package util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {
    public static Connection getConnection() throws  Exception{
        //获取RabbitMQ链接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置链接工厂的各种属性
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5555);
        connectionFactory.setVirtualHost("testhost");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123456");
        //返回一个新的链接对象。
        return connectionFactory.newConnection();
    }

}
