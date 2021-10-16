package backend.restapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ToolsDto {
    private String userId;
    private String userName;
    private long id;
    private String toolName;
    private long cost;
    private String secretName;
}
