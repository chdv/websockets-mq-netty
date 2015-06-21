package com.dch.app.nwmq.netty;

import com.dch.app.nwmq.NwmqException;
import com.dch.app.nwmq.mq.MQBroker;
import com.dch.app.nwmq.mq.MQWorker;
import com.dch.app.nwmq.utils.Utils;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.util.CharsetUtil;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Дмитрий on 21.06.2015.
 */
public enum NettyServerPages {

    INSTANCE;

    private Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);

    private ClassTemplateLoader classTemplateLoader =
            new ClassTemplateLoader(
                    NettyServerPages.class, "/freemaker");

    NettyServerPages() {
        cfg.setTemplateLoader(classTemplateLoader);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    public ByteBuf getIndexPage(String webSocketLocation) {
        Map<String, String> map = new HashMap<>();
        map.put("webSocketLocation", webSocketLocation);
        map.put("stompLocation", MQBroker.BROKER_URL);
        map.put("stompTopic", MQWorker.STOMP_NOTIFY_TOPIC);

        StringWriter stringWriter = new StringWriter();

        try {
            Template template = cfg.getTemplate("page.ftl");
            template.process(map, stringWriter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Unpooled.copiedBuffer(stringWriter.toString(), CharsetUtil.US_ASCII);
    }

    public ByteBuf getResourceFile(String name) {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(name);
        ByteBuf byteBuf = UnpooledByteBufAllocator.DEFAULT.buffer();
        try {
            OutputStream out = new ByteBufOutputStream(byteBuf);
            Utils.copy(stream, out);
        } catch (Exception e) {
            throw new NwmqException(e);
        }
        return byteBuf;
    }

}
