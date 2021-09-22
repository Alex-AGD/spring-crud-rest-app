package backend.restapp.controller;

import backend.restapp.model.Tools;
import backend.restapp.repo.ToolsRepo;
import backend.restapp.service.ToolsService;
import org.assertj.core.api.AbstractBooleanAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ToolsControllerTest {
    @MockBean
    private ToolsRepo toolsRepo;

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
    void testDeleteTool() throws Exception {
        when(this.toolsService.deleteTools(anyLong())).thenReturn(new ResponseEntity<HttpStatus>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/tools{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.toolsController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testGetAllTools() throws Exception {
        when(this.toolsRepo.fgh(any())).thenReturn(1L);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/toolAllDB");
        MockMvcBuilders.standaloneSetup(this.toolsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("1"));
    }


}