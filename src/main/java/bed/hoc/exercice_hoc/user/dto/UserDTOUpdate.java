package bed.hoc.exercice_hoc.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTOUpdate {

    @NotNull
    private Integer id;
    @NotBlank
    private String username;
    @NotBlank
    private String name;
    @NotBlank
    private String firstname;
    @Email
    @NotBlank
    private String email;
    private String password;

}
