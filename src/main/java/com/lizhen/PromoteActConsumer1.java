package com.lizhen;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by lizhen on 2018/9/7.
 */
@Component
public class PromoteActConsumer1 {
    public PromoteActConsumer1(){
        System.out.println("----------------PromoteActConsumer1 created--------------");
    }



    /**
     * 客户端消费
     * @param consumer
     */
    @JmsListener(destination = "promoteAct", containerFactory = "myListenerContainer1",selector = "JMSXGroupID = 'GroupB' ")
    public void receiveQueue(String consumer) {
        System.out.println(Thread.currentThread().getName()+":------ "+consumer+"消息已经消费了");
    }

    @JmsListener(destination = "America.news", containerFactory = "myListenerContainer",id = "client_s1",subscription = "s1")
    public void receiveTopic(String consumer) {
        System.out.println(Thread.currentThread().getName()+":------ "+consumer+"消息已经消费了");
    }
}
