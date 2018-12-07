package com.lizhen;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by lizhen on 2018/9/4.
 */
@Component
public class PromoteActConsumer {

    public PromoteActConsumer(){
        System.out.println("----------------PromoteActConsumer created--------------");
    }



    /**
     * 客户端消费
     * @param consumer
     */
    @JmsListener(destination = "promoteAct", containerFactory = "myListenerContainer1",selector = "JMSXGroupID = 'GroupA' ")
    public void receiveQueue(String consumer) {
        System.out.println(Thread.currentThread().getName()+":>>>>> "+consumer+"消息已经消费了");
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @JmsListener(destination = "America.news", containerFactory = "myListenerContainer")
    public void receiveTopic(String consumer) {
        System.out.println(Thread.currentThread().getName()+":>>>>> "+consumer+"消息已经消费了");
    }
}
