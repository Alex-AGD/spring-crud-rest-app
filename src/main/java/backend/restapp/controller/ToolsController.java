package backend.restapp.controller;


import backend.restapp.model.Tools;
import backend.restapp.repo.ToolsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class ToolsController {
    @Autowired
    ToolsRepo toolsRepo;
    @GetMapping("/tools")
    public ResponseEntity<List<Tools>> getAllTools(@RequestParam(required = false) String title) {
        try {
            List<Tools> tools = new ArrayList<>();
            if (title == null)
                tools.addAll(toolsRepo.findAll());
            if (tools.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tools, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tools/{id}")
    public ResponseEntity<Tools> getToolsById(@PathVariable("id") long id) {
        Optional<Tools> toolsData = toolsRepo.findById(id);
        if (toolsData.isPresent()) {
            return new ResponseEntity<>(toolsData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/tools")
    public ResponseEntity<Tools> createTools(@RequestBody Tools tools) {
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

    @PutMapping("/tools/{id}")
    public ResponseEntity<Tools> update(@PathVariable("id") long id,
                                        @RequestBody Tools tools) {
        Optional<Tools> toolsData = toolsRepo.findById(id);

        if (toolsData.isPresent()) {
            Tools modifyTools = toolsData.get();
            modifyTools.setToolName(tools.getToolName());
            modifyTools.setDateOfTools(tools.getDateOfTools());
            modifyTools.setCost(tools.getCost());
            return new ResponseEntity<>(toolsRepo.save(modifyTools), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/tools{id}")
    public ResponseEntity <HttpStatus> deleteTool(@PathVariable("id") long id){
        try {
            toolsRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
