package backend.restapp.controller;

import backend.restapp.model.Tools;
import backend.restapp.service.ToolsService;
import org.assertj.core.api.AbstractBooleanAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ToolsControllerTest {
    @Autowired
    ToolsService toolsService;
    @Autowired
    ToolsController toolsController;
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getTools() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "api/tools", String.class))
                .contains("{\"id\":1,\"toolName\":\"Газпром\",\"dateOfTools\":\"2012-09-17\",\"cost\":1000}," +
                        "{\"id\":2,\"toolName\":\"Сбербанк\",\"dateOfTools\":\"2012-09-17\",\"cost\":3000}," +
                        "{\"id\":3,\"toolName\":\"Автоваз\",\"dateOfTools\":\"2012-09-17\",\"cost\":1000}");
    }

    @Test
    void getToolsById() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "api/tools/1", String.class))
                .contains("{\"id\":1,\"toolName\":\"Газпром\",\"dateOfTools\":\"2012-09-17\",\"cost\":1000}");
    }

    @Test
    void createTools() {
        HttpEntity<Tools> request = new HttpEntity<>(new Tools("TestTools", new Date(), 123L));
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "api/tools", request, Tools.class))
                .isNotNull();
        ResponseEntity<Tools> response = restTemplate
                .exchange("http://localhost:" + port + "api/tools", HttpMethod.POST, request, Tools.class);
        AbstractBooleanAssert<?> abstractBooleanAssert = assertThat(response.getStatusCode().equals(HttpStatus.CREATED));
        abstractBooleanAssert.isTrue();
    }


    @Test
    void update() {
    }

    @Test
    void deleteTool() {
    }
}