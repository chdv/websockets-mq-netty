package com.dch.app.nwmq.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

/**
 * Created by ִלטענטי on 21.06.2015.
 */
public class MQProducer {

    private static Logger logger = LoggerFactory.getLogger(MQProducer.class);

    private MQWorker worker = null;
    private MessageProducer producer = null;

    public MQProducer() throws JMSException {
        worker = new MQWorker();
        producer = worker.getSession().createProducer(worker.getDestination());
    }

    public void publish(String message)  throws JMSException  {
        publish(message, DeliveryMode.NON_PERSISTENT);
    }

    public void publish(String message, int deliveryMode) throws JMSException {
        TextMessage textMessage = worker.getSession().createTextMessage(message);
        textMessage.setJMSDeliveryMode(deliveryMode);
        producer.send(textMessage);
        logger.debug("Message sent to subscribers: '{}'", message);
    }

    public void close() throws JMSException {
        producer.close();
        worker.close();
    }
}
