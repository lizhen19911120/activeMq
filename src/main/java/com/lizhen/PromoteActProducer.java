package com.lizhen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Topic;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * Created by lizhen on 2018/9/4.
 */

@Component
@EnableScheduling
public class PromoteActProducer {

    private static Boolean flag=false;

    public PromoteActProducer() {
        System.out.println("----------PromoteActProducer created--------------");
    }

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    @Autowired
    private Topic topic;

    @Autowired
    private MessagePostProcessor messagePostProcessor;

    private static int count1;
    private static int count2;
    private static int count3;

    @Scheduled(fixedDelay = 3000)    // 每3s执行1次
    public void send() {
//        ActiveMQTextMessage message = new ActiveMQTextMessage();
//        try {
//            message.setText("hello,activeMQ");
//        } catch (MessageNotWriteableException e) {
//
//        }

        //交替分发不同分组的消息
        Map<String,Object> group = new HashMap<>();
        if(flag){
            group.put("JMSXGroupID", "GroupA");
            flag=false;
        }else {
            group.put("JMSXGroupID", "GroupB");
            flag=true;
        }


        Message message1 = new Message() {
            @Override
            public Object getPayload() {
                return "hello,activeMQ"+ count1++;
            }

            //设置消息分组信息
            @Override
            public MessageHeaders getHeaders() {
                return new MessageHeaders(group);
            }
        };
        System.out.println(message1.getHeaders().entrySet());

        Message message2 = new Message() {
            @Override
            public Object getPayload() {
                return "hello,here is a American news"+ count2++;
            }

            @Override
            public MessageHeaders getHeaders() {
                return new MessageHeaders(null);
            }

        };
        Message message3 = new Message() {
            @Override
            public Object getPayload() {
                return "hello, synchronized activeMQ"+ count3++;
            }

            @Override
            public MessageHeaders getHeaders() {
                return new MessageHeaders(null);
            }

        };
        //send(destination,message)方法只能发送到queque里，不能发送到topic中
        // （跟踪方法发现JmsDestinationAccessor中的pubSubDomain默认是false，也就是只能将destination解析为queue）
        this.jmsMessagingTemplate.send("promoteAct" , message1);
        System.out.println(Thread.currentThread().getName()+":hello,activeMQ消息已经发送了");
        this.jmsMessagingTemplate.convertAndSend(topic,message2.getPayload());
        System.out.println(Thread.currentThread().getName()+":hello,here is a American news消息已经发送了");
        this.jmsMessagingTemplate.send("promoteAct1",message3);
        System.out.println(Thread.currentThread().getName()+":hello, synchronized activeMQ消息已经发送了");
    }
}
