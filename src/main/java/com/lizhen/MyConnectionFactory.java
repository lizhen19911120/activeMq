package com.lizhen;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Created by lizhen on 2018/9/11.
 * 定义自己的连接ActiveMQ的连接工厂，这里没有用到
 */
@Deprecated
public class MyConnectionFactory extends ActiveMQConnectionFactory {
    @Override
    public void setClientID(String clientID) {
        super.setClientID("client_zhe");
    }
}
