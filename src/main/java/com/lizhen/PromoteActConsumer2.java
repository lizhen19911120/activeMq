package com.lizhen;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by lizhen on 2018/9/11.
 */
@Component
public class PromoteActConsumer2 {
    public PromoteActConsumer2(){
        System.out.println("----------------PromoteActConsumer2 created--------------");
    }

    /**
     * 客户端消费
     * @param consumer
     */
    @JmsListener(destination = "America.news", containerFactory = "myListenerContainer2")
    public void receiveQueue(String consumer) {
        System.out.println(Thread.currentThread().getName()+":------ "+consumer+"消息已经消费了");
    }
}
