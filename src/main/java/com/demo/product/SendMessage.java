package com.demo.product;




import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
 
import org.apache.activemq.ActiveMQConnectionFactory;
 
public class SendMessage {
    private static final String url = "tcp://localhost:61616";
    private static final String QUEUE_NAME = "G2Queue1";
 
    public void sendMessage() throws JMSException {
       // JMS 客户端到JMSProvider 的连接
       Connection connection = null;
       Session session = null;
       try {
           // 连接工厂，JMS 用它创建连接 
           // 构造ConnectionFactory实例对象，此处采用ActiveMq的实现jar
           ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
           connection = (Connection)connectionFactory.createConnection();
           // 启动连接
           connection.start();
           //Session：发送或接收消息的线程
           // 获取session
           session = (Session) connection.createSession(false,
                  Session.AUTO_ACKNOWLEDGE);
           // 消息的目的地，消息发送到那个队列
           Destination destination = session.createQueue(QUEUE_NAME);
           //MessageProducer：消息发送者（生产者）
           // 创建消息发送者
           MessageProducer producer =session.createProducer(destination);
           // 设置是否持久化
           //DeliveryMode.NON_PERSISTENT：不持久化
           //DeliveryMode.PERSISTENT：持久化
           producer.setDeliveryMode(DeliveryMode.PERSISTENT);
          
           String msg = "";
           int i = 0;
        do {
            msg = "第"+i + "次发送的消息";
                TextMessage message = session.createTextMessage(msg);
                Thread.sleep(1000);
                // 发送消息到目的地方
               producer.send(message);
                System.out.println("发送消息：" +msg);
                i++;
        } while (i<10);
       } catch (Exception e) {
           e.printStackTrace();
       }
       finally {
    		   session.close();
    		   connection.close();
	}
    }
 
    public static void main(String[] args) {
       SendMessage sndMsg = new SendMessage();
       try {
           sndMsg.sendMessage();
       } catch (Exception ex) {
           System.out.println(ex.toString());
       }
    }
}