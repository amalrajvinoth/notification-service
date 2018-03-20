package in.amal.gns.web;

import in.amal.gns.model.ChannelType;
import in.amal.gns.model.Message;
import in.amal.gns.service.ChannelFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/notifier")
@Api(value="Notification APIs")
public class NotificationController {

    private static final Logger LOG = LogManager.getLogger(NotificationController.class);

    @Autowired
    private ChannelFactory factory;

    @ApiOperation(value = "Notify the given message to given channelType.")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Not Found")})
    @PostMapping("/notify/{channelType}")
    public void notify(@PathVariable ChannelType channelType, @RequestBody Message msg) {
        factory.get(channelType).notify(msg);
    }

    @ApiOperation(value = "Notify the given message to all channels like Slack and email.")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "Not Found")})
    @PostMapping("/notifyAll")
    public void notifyAll(@RequestBody Message msg) {
        factory.notifyAll(msg);
    }
}
