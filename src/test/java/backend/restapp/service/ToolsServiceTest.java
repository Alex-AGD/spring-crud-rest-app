package backend.restapp.service;

import backend.restapp.model.Tools;
import backend.restapp.repo.ToolsRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ToolsServiceTest {
    @Autowired
    ToolsRepo toolsRepo;

    @Test
    public void createTools() {
        Tools newTools = new Tools();
        newTools.setToolName("Test");
        newTools.setCost(1L);
        newTools.setDateOfTools(new Date());
        toolsRepo.save(newTools);
        assertNotNull(toolsRepo.findById(1L).get());
    }

    @Test
    public void findAllTools() {
        List<Tools> toolsRepoAll = toolsRepo.findAll();
        List<Tools> tools = new ArrayList<>(toolsRepoAll);
        assertThat(tools).size().isGreaterThanOrEqualTo(0);
    }

    @Test
    public void getToolByID() {
        Tools toolsRepoById = toolsRepo.findById(1L).get();
        assertEquals(1000, toolsRepoById.getCost());
    }
}