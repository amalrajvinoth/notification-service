package in.amal.gns.swagger;

import java.util.List;

import com.fasterxml.classmate.TypeResolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    // prefix for api version 1
    private static final String V1 = "v1.0"; 
    
    @Autowired
    private TypeResolver typeResolver;

    /**
     * Initialize group for API ver 1 
     */
    @Bean
    public Docket v1Api() {
        List<ResponseMessage> resp = buildGlobalResponses();
        ApiInfo info = buildApiInfo(V1);

        return new Docket(DocumentationType.SWAGGER_2)
        .useDefaultResponseMessages(true)
        .globalResponseMessage(RequestMethod.GET, resp)
        .globalResponseMessage(RequestMethod.POST, resp)
        .globalResponseMessage(RequestMethod.PUT, resp)
        .globalResponseMessage(RequestMethod.DELETE, resp)
        .apiInfo(info)
        .groupName(V1)
        .select()
          .paths(regex(".*/"+V1+".*")) 
          .build();
    }

    
    private ApiInfo buildApiInfo(String ver) {
        return new ApiInfoBuilder()
                .title("Generic Notification System")
                .description("REST API for Notification Service")
                .version(ver)
                .build();
    }
    
    private List<ResponseMessage> buildGlobalResponses() {
        return newArrayList(new ResponseMessageBuilder()
            .code(500)
            .message("Unexpected error during execution")
            .responseModel(new ModelRef("Error"))
            .build());    
    }
    
}