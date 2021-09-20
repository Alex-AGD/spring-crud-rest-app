package backend.restapp.service;

import backend.restapp.model.Tools;
import backend.restapp.repo.ToolsRepo;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ToolsServiceTest {
    @Autowired
    ToolsRepo toolsRepo;

    @Test
    @Order(2)
    void createTools() {
        Tools newTools = new Tools();
        newTools.setToolName("Test");
        newTools.setCost(1L);
        newTools.setDateOfTools(new Date());
        toolsRepo.save(newTools);
        assertNotNull(toolsRepo.findById(1L).orElse(null));
    }

    @Test
    @Order(1)
    void findAllTools() {
        List<Tools> toolsRepoAll = toolsRepo.findAll();
        List<Tools> tools = new ArrayList<>(toolsRepoAll);
        assertThat(tools).size().isNotNegative();
    }

    @Test
    @Order(3)
    void getToolByID() {
        Tools toolsRepoById = toolsRepo.findById(1L).orElse(null);
        assert toolsRepoById != null;
        assertEquals(1000, toolsRepoById.getCost());
    }

    @Test
    @Order(4)
    void updateTool() {
        Tools toolsRepoById = toolsRepo.findById(1L).orElse(null);
        assert toolsRepoById != null;
        toolsRepoById.setToolName("TestName");
        toolsRepoById.setCost(333L);
        assertNotEquals(1000, toolsRepoById.getCost());
        assertNotEquals("Газпром", toolsRepoById.getToolName());
    }

    @Test
    @Order(5)
    void deleteById() {
        toolsRepo.deleteById(1L);
        //existsById(ID id) - возвращает логическое значение
        assertThat(toolsRepo.existsById(1L)).isFalse();
    }

}