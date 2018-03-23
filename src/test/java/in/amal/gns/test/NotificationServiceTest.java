
package in.amal.gns.test;

import in.amal.gns.model.ChannelType;
import in.amal.gns.model.Message;
import in.amal.gns.service.NotificationService;
import in.amal.gns.service.channel.Channel;
import in.amal.gns.service.channel.ChannelFactory;
import in.amal.gns.service.channel.EmailChannel;
import in.amal.gns.service.channel.SlackChannel;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NotificationServiceTest {
    private NotificationService service;
    private ChannelFactory factory;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        List<Channel> channelList = new ArrayList<>(2);
        channelList.add(new SlackChannel());
        channelList.add(new EmailChannel());
        factory = new ChannelFactory(channelList);
        service = new NotificationService(factory);
    }

    @After
    public void tearDown() throws Exception {
    }

    /*@Test
    public void testNotifySlack() {
        Message msg = generateMessage();
        when(service.notify(ChannelType.slack, msg)).thenReturn(anyLong());

        assertThat(service.notify(ChannelType.slack, msg), is(anyLong()));
    }

    @Test
    public void testNotifyEmail() {
        Message msg = generateMessage();
        when(service.notify(ChannelType.email, msg)).thenReturn(anyLong());

        assertThat(service.notify(ChannelType.email, msg), is(anyLong()));
    }

    @Test
    public void testNotifyAll() {
        Message msg = generateMessage();
        when(service.notifyAll(msg)).thenReturn(anyLong());

        assertThat(service.notifyAll(msg), is(Long.class));
    }*/

    @Test(expected = RuntimeException.class)
    public void testEmailMessageInvalid() {
        Message msg = generateMessage();
        msg.setFrom("invalid_mail_format");
        when(service.notify(ChannelType.email, msg)).thenReturn(anyLong());

        assertThat(service.notify(ChannelType.email, msg), is(anyLong()));
    }

    private Message generateMessage() {
        Message msg = new Message();
        msg.setFrom("sender@gmail.com");
        msg.setTo("receiver@gmail.com");
        msg.setSubject("Test Subject  - Unit Test");
        msg.setBody("Body of Message");
        return msg;
    }
}
