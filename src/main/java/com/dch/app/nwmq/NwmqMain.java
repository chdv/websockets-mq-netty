package com.dch.app.nwmq;

import com.dch.app.nwmq.mq.MQBroker;
import com.dch.app.nwmq.mq.MQConsumer;
import com.dch.app.nwmq.netty.NettyServer;

/**
 * Created by ִלטענטי on 21.06.2015.
 */
public class NwmqMain {

    public static void main(String[] args) throws Exception {
        MQBroker helper = new MQBroker();
        helper.start();

        new MQConsumer();

        NettyServer.startWSServer();
    }
}
