package backend.restapp.service;

import backend.restapp.model.Tools;
import backend.restapp.repo.ToolsRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ToolsService {

    private final ToolsRepo toolsRepo;

    public ToolsService(ToolsRepo toolsRepo) {
        this.toolsRepo = toolsRepo;
    }


    public ResponseEntity<List<Tools>> getAllTools(String title) {
        try {
            List<Tools> tools = new ArrayList<>();
            toolsRepo.findAll();
            if (title == null)
                tools.addAll(toolsRepo.findAll());
            if (tools.isEmpty()) {
                new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tools, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Tools> getToolByID(long id) {
        Optional<Tools> toolsData = toolsRepo.findById(id);
        if (toolsData.isPresent()) {
            return new ResponseEntity<>(toolsData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<Tools> createTools(Tools tools) {
        try {
            Tools newTools = toolsRepo.save(new Tools(
                    tools.getToolName(),
                    tools.getDateOfTools(),
                    tools.getCost()));

            return new ResponseEntity<>(newTools, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Tools> updateTool(long id, Tools tools) {
        Optional<Tools> toolsData = toolsRepo.findById(id);
        if (toolsData.isPresent()) {
            Tools modifyTools = toolsData.get();
            modifyTools.setToolName(tools.getToolName());
            modifyTools.setDateOfTools(tools.getDateOfTools());
            modifyTools.setCost(tools.getCost());
            return new ResponseEntity<>(toolsRepo.save(modifyTools), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<HttpStatus> deleteTools(long id) {
        try {
            toolsRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

