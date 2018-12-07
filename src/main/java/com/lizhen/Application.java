package com.lizhen;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.jms.listener.SimpleMessageListenerContainer;

import javax.jms.ConnectionFactory;

/**
 * Created by lizhen on 2018/9/4.
 */
@SpringBootApplication(scanBasePackages = {"com.lizhen","com.log"})
//@EnableAspectJAutoProxy(proxyTargetClass=true)
public class Application {

    /**
     * 定义ActiveMQ的队列
     * @return ActiveMQ的queue
     */
    @Bean
    public ActiveMQQueue queue() {
        return new ActiveMQQueue("promoteAct");
    }

    /**
     * 定义ActiveMQ的主题
     * @return ActiveMQ的topic
     */
    @Bean
    public ActiveMQTopic topic() {
        return new ActiveMQTopic("America.news");
    }

    /**
     * 定义自己的连接ActiveMQ的连接工厂，这里没有用到
     * @return
     */
    @Bean
    public ConnectionFactory MyConnectionFactory(){
        return new MyConnectionFactory();
    }

    /**
     * 定义ListenerContainer容器，这里用在topic消息上
     * @param connectionFactory spring返回的默认连接工厂
     * @return
     */
    @Bean
    public JmsListenerContainerFactory<?> myListenerContainer(ConnectionFactory connectionFactory){
        System.out.println("-----------myListenerContainer created--------------");
        MySimpleJmsListenerContainerFactory factory = new MySimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }


    /**
     * 定义ListenerContainer容器，这里用在queue消息上
     * @param connectionFactory spring返回的默认连接工厂
     * @return
     */
    @Bean
    public JmsListenerContainerFactory<?> myListenerContainer1(ConnectionFactory connectionFactory){
        System.out.println("-----------myListenerContainer1 created--------------");
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //配置多少个消费任务实例，5-10表示默认每个消费者开5个线程消费，最大10个线程消费；10表示最大开10个线程消费，默认开1个线程消费
//        factory.setConcurrency("2-3");
        //配置JMS缓存级别
//        factory.setCacheLevel();
        return factory;
    }

    /**
     * 定义ListenerContainer容器，这里用在持久化topic消息上
     * @param connectionFactory spring返回的默认连接工厂
     * @return
     */
    @Bean
    public JmsListenerContainerFactory<?> myListenerContainer2(ConnectionFactory connectionFactory){
        System.out.println("-----------myListenerContainer2 created--------------");
        MySimpleJmsListenerContainerFactory factory = new MySimpleJmsListenerContainerFactory(){
            @Override
            protected void initializeContainer(SimpleMessageListenerContainer instance) {
                instance.setSubscriptionDurable(true);
                instance.setSubscriptionName("Subscription_lizhen");
                super.initializeContainer(instance);
            }
        };
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);
        factory.setClientId("client_lizhen");
        return factory;
    }

    /**
     * 定义ListenerContainer容器，这里用在持久化topic消息上
     * @param connectionFactory spring返回的默认连接工厂
     * @return
     */
    @Bean
    public JmsListenerContainerFactory<?> myListenerContainer3(ConnectionFactory connectionFactory){
        System.out.println("-----------myListenerContainer3 created--------------");
        MySimpleJmsListenerContainerFactory factory = new MySimpleJmsListenerContainerFactory(){
            @Override
            protected void initializeContainer(SimpleMessageListenerContainer instance) {
                instance.setSubscriptionDurable(true);
                super.initializeContainer(instance);
            }
        };
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);
        factory.setClientId("client_lizhen1");
        return factory;
    }



    /**
     * 定义一个对发送的消息的后处理器,这里没有用到
     * @return
     */
    @Bean
    public MessagePostProcessor messagePostProcessor(){
        return message -> message;
    }


    /**
     * 定义默认内置tomcat的http连接属性
     * @version
     * @author liuyi  2016年7月20日 下午7:59:41
     */
    class GwsTomcatConnectionCustomizer implements TomcatConnectorCustomizer {

        public GwsTomcatConnectionCustomizer() {
        }

        @Override
        public void customize(Connector connector) {
            connector.setPort(8082);
//            connector.setAttribute("connectionTimeout", connectionTimeout);
//            connector.setAttribute("acceptorThreadCount", acceptorThreadCount);
//            connector.setAttribute("minSpareThreads", minSpareThreads);
//            connector.setAttribute("maxSpareThreads", maxSpareThreads);
//            connector.setAttribute("maxThreads", maxThreads);
//            connector.setAttribute("maxConnections", maxConnections);
//            connector.setAttribute("protocol", protocol);
//            connector.setAttribute("redirectPort", "redirectPort");
//            connector.setAttribute("compression", "compression");
        }
    }

    /**
     * 定义springboot启动的内置tomcat容器
     * @return
     */
    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addConnectorCustomizers(new GwsTomcatConnectionCustomizer());
        return tomcat;
    }

    protected static Logger logger= LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        //变更配置文件读取位置启动
//        new SpringApplicationBuilder(Application.class)
//                .properties("spring.config.location=classpath:/config/springbootconfig/springbootconfig.properties")
//                .run(args);
        //变更配置文件名字启动
//        new SpringApplicationBuilder(Application.class)
//                .properties("spring.config.name=Myapplication")
//                .run(args);
        ApplicationContext context = new SpringApplicationBuilder(Application.class)
                .properties("server.port=8081")
                .run(args);
//        SpringApplication.run(Application.class, args);
        logger.info("SpringBoot Start Success");

//        PromoteActConsumer3 consumer3 = (PromoteActConsumer3)context.getBean("promoteActConsumer3");
//        consumer3.recive();
    }


}