package in.amal.gns.service;

import in.amal.gns.model.ChannelType;
import in.amal.gns.model.Message;
import in.amal.gns.service.channel.Channel;
import in.amal.gns.service.channel.ChannelFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class NotificationService {

    private static final Logger LOG = LogManager.getLogger(NotificationService.class);

    @Autowired
    ChannelFactory factory;

    public NotificationService(ChannelFactory factory) {
        this.factory = factory;
    }

    private AtomicInteger notificationId = new AtomicInteger(1);

    /**
     * Notifies channel identified by given channelType with the given message.
     * @param msg The message includes from, to, subject, body
     */
    public long notifyAll(Message msg) {
        for(Channel c : factory.getChannels()) {
            msg.setId(notificationId.getAndIncrement());
            c.notify(msg);
            LOG.debug("ID = "+notificationId+", Message sent = "+msg);
        }
        return notificationId.longValue();
    }

    /**
     * Notifies all configured channels(like slack and email) with the given message.
     * @param channelType Type of chanel to notify - slack and email
     * @param msg The message includes from, to, subject, body
     */
    public long notify(ChannelType channelType, Message msg) {
        msg.setId(notificationId.getAndIncrement());
        factory.get(channelType).notify(msg);
        LOG.debug("ID = "+notificationId+", Message sent = "+msg);
        return notificationId.longValue();
    }
}
