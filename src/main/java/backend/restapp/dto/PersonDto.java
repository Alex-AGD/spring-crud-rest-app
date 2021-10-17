package backend.restapp.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PersonDto {
    private String id;
    private String email;
    private String userName;
    private String lastName;
    private String password;
}
