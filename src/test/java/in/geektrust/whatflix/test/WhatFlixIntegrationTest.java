package in.geektrust.whatflix.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.geektrust.whatflix.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
public class WhatFlixIntegrationTest {

	private static final String BASE_URL = "/api/v1.0/whatflix";

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Value("classpath:search_result_100_tom_hanks.json")
    Resource resourceApi1;

    @Value("classpath:movie_by_user_pref.json")
    Resource resourceApi2;

    @LocalServerPort
    private int port;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testFindMovies() throws Exception {

        String url = prepareUrl(100, "Tom Hanks");

        System.out.println("URL: "+url);
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(preparedExpectedJsonString(resourceApi1)))
                .andExpect(jsonPath("$", hasSize(28)));

        //JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testFindMoviesByUserPreference() throws Exception {
        String url = prepareUrl("/movies/users");
        System.out.println("URL: "+url);
        mvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(preparedExpectedJsonString(resourceApi2)))
                .andExpect(jsonPath("$", hasSize(7)));
    }

    private String prepareUrl(String uri) {
        StringBuilder url = new StringBuilder().append(uri);
        return createURLWithPort(url.toString());
    }

    private String prepareUrl(int user, String searchText) {
        //movies/user/100/search?text=Tom%20Hanks
        StringBuilder url = new StringBuilder()
                .append("/movies/user/").append(user).append("/search")
                .append("?text=").append(searchText);
        return createURLWithPort(url.toString());
    }

    private String createURLWithPort(String uri) {
        return new StringBuilder("http://localhost:").append(port ).append(BASE_URL)+uri;
    }

    private String preparedExpectedJsonString(Resource resource) throws IOException{
        String jsonStr = new String(Files.readAllBytes(Paths.get(resource.getFile().toURI())));
        Object o = objectMapper.readValue(jsonStr, Object.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
    }

    private String mvcResponseToJson(String jsonStr) throws IOException {
        Object o = objectMapper.readValue(jsonStr, Object.class);
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
    }
}