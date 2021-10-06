package backend.restapp.mappers;

import backend.restapp.dto.ToolsDto;
import backend.restapp.model.Tools;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {
    public ToolsDto mapToToolsDto(Tools tools) {
        ToolsDto dto = new ToolsDto();
        dto.setId(tools.getId());
        dto.setCost(tools.getCost());
        dto.setToolName(tools.getToolName());
        return dto;
    }

    public Tools mapToTools(ToolsDto toolsDto){
        Tools tools = new Tools();
        tools.setCost(toolsDto.getCost());
        tools.setToolName(toolsDto.getToolName());
        return tools;
    }

/*    public List<ToolsDto> toolsDtoList(List<ToolsDto> toolsList){
        return toolsList.stream()
                .map(this::mapToToolsDto)
                .collect(Collectors.toList());
    }*/
}
