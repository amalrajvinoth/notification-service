package in.amal.gns.service.channel;

import in.amal.gns.model.Message;
import in.amal.gns.model.ChannelType;

public interface Channel {
    default void notify(Message msg) {
        throw new RuntimeException("Notify method is not implemented yet.");
    }

    default boolean supports(ChannelType type) {
        throw new RuntimeException("Notify method is not implemented yet.");
    }
}
