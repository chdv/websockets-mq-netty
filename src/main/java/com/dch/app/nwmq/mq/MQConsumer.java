package com.dch.app.nwmq.mq;

import com.dch.app.nwmq.NwmqException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * Created by ִלטענטי on 21.06.2015.
 */
public class MQConsumer {

    private static Logger logger = LoggerFactory.getLogger(MQConsumer.class);

    private MessageConsumer subscriber = null;
    private MQWorker worker = null;

    static MessageListener defaultListener = new MessageListener() {
        public void onMessage(Message message) {
            try {
                String text = null;
                if(message instanceof TextMessage) {
                    text = ((TextMessage) message).getText();
                } else if(message instanceof BytesMessage) {
                    BytesMessage bm = (BytesMessage) message;
                    byte data[] = new byte[(int) bm.getBodyLength()];
                    bm.readBytes(data);
                    text = new String(data, "UTF-8");
                }

                if(logger.isDebugEnabled()) {
                    logger.debug(
                        "Message received from : '{}', message: '{}', JMSDeliveryMode: {}",
                        message.getJMSDestination(), text, message.getJMSDeliveryMode());
                }
            } catch (Exception e) {
                throw new NwmqException(e);
            }
        }
    };

    public MQConsumer() throws JMSException {
        this(defaultListener);
    }

    public MQConsumer( MessageListener listener) throws JMSException {
        worker = new MQWorker();
        subscriber = worker.getSession().createConsumer(worker.getDestination());
        subscriber.setMessageListener(listener);
    }

    public void close() throws JMSException {
        subscriber.close();
        worker.close();
    }
}
