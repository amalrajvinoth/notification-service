package in.amal.gns.swagger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class SwaggerRedirectController {
    @RequestMapping(value = "/api", method = RequestMethod.GET)
    public ModelAndView redirect1() {
        return new ModelAndView("redirect:/swagger-ui.html");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView redirect2() {
        return new ModelAndView("redirect:/swagger-ui.html");
    }

    @RequestMapping(value = "/doc", method = RequestMethod.GET)
    public ModelAndView redirect3() {
        return new ModelAndView("redirect:/swagger-ui.html");
    }

}
