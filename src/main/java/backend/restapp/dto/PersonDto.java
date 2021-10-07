package backend.restapp.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PersonDto {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
