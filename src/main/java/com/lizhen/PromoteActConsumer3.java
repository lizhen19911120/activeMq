package com.lizhen;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * 同步消息消费者
 * Created by lizhen on 2018/9/12.
 */
@Component
public class PromoteActConsumer3 {

    public PromoteActConsumer3(){
        System.out.println("----------------PromoteActConsumer3 created--------------");
    }

    private String name = "";

    private Destination destination = null;

    private Connection connection = null;

    private Session session = null;

    private MessageConsumer consumer = null;

    public  void initialize() throws JMSException
    {
        ConnectionFactory connectFactory = new ActiveMQConnectionFactory(
                "tcp://2013-20161103LI:61616");
        Connection connection = connectFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination =  session.createQueue("promoteAct1");
        consumer = session.createConsumer(destination);
    }

    public void recive()
    {
        try {
            initialize();
            System.out.println("PromoteActConsumer3:->Begin listening...");
            int count=0;
            while(true)
            {
                Message message = consumer.receive(); //主动接收消息(同步)
                System.out.println("consumer recive:"+((TextMessage)message).getText());
                count++;
                System.out.println(count);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            try {
                close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    // 关闭连接
    public void close() throws JMSException {
        System.out.println("Consumer:->Closing connection");
        if (consumer != null)
            consumer.close();
        if (session != null)
            session.close();
        if (connection != null)
            connection.close();
    }

}
