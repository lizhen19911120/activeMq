package com.lizhen;

import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.listener.SimpleMessageListenerContainer;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 自定义创建ListenerContainer的工厂类，这里是继承SimpleJmsListenerContainerFactory（当然也可以继承DefaultMessageListenerContainer等内置类）
 * 可以自定义ListenerContainer的配置细节
 * Created by lizhen on 2018/9/11.
 */

public class MySimpleJmsListenerContainerFactory extends SimpleJmsListenerContainerFactory {

    @Override
    protected void initializeContainer(SimpleMessageListenerContainer instance) {
        //设置ListenerContainer使用的线程执行器
        instance.setTaskExecutor(new ThreadPoolExecutor(10, 10, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>()));
//        //设置topic是否持久化
//        instance.setSubscriptionDurable(true);
//        //配置同步消费消息的消费者数量，通俗点就是让原本2个消费者开2个线程去执行消费任务变成2个消费者各开3个线程去执行消费任务，即从2个消费任务变成6个消费任务，在topic模式下会产生重复消费不建议设置，queue模式下每条消息还是一个消费者消费，但可能在不同的线程任务下消费。
//        instance.setConcurrentConsumers(3);
//        //类似setConcurrentConsumers()
//        instance.setConcurrency("3");
        super.initializeContainer(instance);
    }
}
