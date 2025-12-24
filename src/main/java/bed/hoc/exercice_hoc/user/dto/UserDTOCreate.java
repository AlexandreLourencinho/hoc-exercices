package bed.hoc.exercice_hoc.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserDTOCreate {

    @NotBlank
    private String username;
    @NotBlank
    private String name;
    @NotBlank
    private String firstname;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;

}
