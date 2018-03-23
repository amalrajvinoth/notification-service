package in.amal.gns.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.amal.gns.Main;
import in.amal.gns.model.ChannelType;
import in.amal.gns.model.Message;
import in.amal.gns.web.NotificationController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.hamcrest.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
public class NotificationServiceIntegrationTest {

    private static final String BASE_URL = "/api/v1.0/notifier";
    private static final String HOST = "http://localhost:";

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @Value("${spring.mail.username}")
    String user;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testNotifySuccess() throws Exception {
        String url = prepareUrl(ChannelType.slack);
        mvc.perform(post(url)
                .content(generateMessageJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testNotifyAllSuccess() throws Exception {
        String url = prepareUrl();
        mvc.perform(post(url)
                .content(generateMessageJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testNotifySlackSuccess() throws Exception {
        String url = prepareUrl(ChannelType.slack);
        mvc.perform(post(url)
                .content(generateMessageJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(handler().handlerType(NotificationController.class))
                .andExpect(handler().methodName("notify"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", any(Number.class)));
    }

    @Test
    public void testNotifyEmailSuccess() throws Exception {
        String url = prepareUrl(ChannelType.email);
        mvc.perform(post(url)
                .content(generateMessageJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(handler().handlerType(NotificationController.class))
                .andExpect(handler().methodName("notify"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", any(Number.class)));
    }

    @Test
    public void testEmailMessageInvalid() throws Exception {
        String url = prepareUrl(ChannelType.email);
        mvc.perform(post(url)
                .content(generateInvalidMessageJson())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private String prepareUrl() {
        // http://localhost:8080//api/v1.0/notifier/notifyAll
        return prepareURLWithPort("/notifyAll");
    }

    private String prepareUrl(ChannelType type) {
        // http://localhost:8080//api/v1.0/notifier/notify/{type}
        StringBuilder url = new StringBuilder().append("/notify/").append(type);
        return prepareURLWithPort(url.toString());
    }

    private String prepareURLWithPort(String uri) {
        return new StringBuilder(HOST).append(port).append(BASE_URL).append(uri).toString();
    }

    private Message generateMessage() {
        Message msg = new Message();
        msg.setFrom(user);
        msg.setTo(user);
        msg.setSubject("Test Subject - Integration Test");
        msg.setBody("Body of Message");
        return msg;
    }

    private String generateMessageJson() throws IOException {
        return objectMapper.writeValueAsString(generateMessage());
    }

    private String generateInvalidMessageJson() throws IOException {
        Message msg = generateMessage();
        msg.setFrom("invalid-domain");
        return objectMapper.writeValueAsString(msg);
    }
}