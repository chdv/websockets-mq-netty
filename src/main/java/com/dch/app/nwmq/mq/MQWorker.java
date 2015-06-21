package com.dch.app.nwmq.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by ִלטענטי on 21.06.2015.
 */
public class MQWorker {

    public final static String VM_MQ_URL = "vm://localhost?create=false&broker.persistent=false";

    public static String NOTIFY_TOPIC = "topic.notify";

    public static String STOMP_NOTIFY_TOPIC = "/topic/topic.notify";

    private ConnectionFactory connectionFactory = null;
    private Connection connection = null;
    private Session session = null;
    private Destination destination = null;

    public MQWorker() throws JMSException {
        connectionFactory = new ActiveMQConnectionFactory(VM_MQ_URL);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createTopic(NOTIFY_TOPIC);
    }

    public Session getSession() {
        return session;
    }

    public Destination getDestination() {
        return destination;
    }

    public void close() throws JMSException {
        connection.stop();
        connection.close();
    }
}
