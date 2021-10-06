package backend.restapp.controller;

import backend.restapp.dto.ToolsDto;
import backend.restapp.model.Tools;
import backend.restapp.service.ToolsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class ToolsController {

    private final ToolsService toolsService;

    public ToolsController(ToolsService toolsService) {
        this.toolsService = toolsService;
    }


    @GetMapping("/tools")
    public ResponseEntity<List<Tools>> getTools(@RequestParam(required = false) String title) {
        return toolsService.getAllTools(title);
    }

    @GetMapping("/tools/dto")
    public List<ToolsDto> getToolsDto(@RequestParam(required = false) String title) {
//        return mappingUtils.toolsDtoList(toolsService.getAllToolsDto());
        return null;
    }

    @GetMapping("/tools/{id}")
    public ResponseEntity<Tools> getToolsById(@PathVariable("id") long id) {
        return toolsService.getToolByID(id);
    }

    @PostMapping("/tools")
    public ResponseEntity<Tools> createTools(@RequestBody Tools tools) {
        return toolsService.createTools(tools);
    }

    @PutMapping("/tools/{id}")
    public ResponseEntity<Tools> update(@PathVariable("id") long id,
                                        @RequestBody Tools tools) {
        return toolsService.updateTool(id, tools);
    }

    @DeleteMapping("/tools/{id}")
    public ResponseEntity<HttpStatus> deleteTool(@PathVariable("id") long id) {
        return toolsService.deleteTools(id);
    }
}

