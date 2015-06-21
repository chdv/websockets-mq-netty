package com.dch.app.nwmq.mq;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.ManagementContext;

/**
 * Created by ִלטענטי on 21.06.2015.
 */
public class MQBroker {

    public static String BROKER_URL = "ws://127.0.0.1:61614";

    private BrokerService broker = new BrokerService();

    public MQBroker() throws Exception {
        broker = new BrokerService();
        broker.addConnector( BROKER_URL );
        broker.setPersistent( false );

//        ActiveMQTopic topic = new ActiveMQTopic( NOTIFY_TOPIC );
//        broker.setDestinations( new ActiveMQDestination[] { topic }  );

        ManagementContext managementContext = new ManagementContext();
        managementContext.setCreateConnector( true );
        broker.setManagementContext( managementContext );
    }

    public void stop() throws Exception {
        broker.stop();
    }

    public void start() throws Exception {
        broker.start();
    }
}
