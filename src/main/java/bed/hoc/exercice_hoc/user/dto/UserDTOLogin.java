package bed.hoc.exercice_hoc.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTOLogin {

    @NotBlank
    private String usernameOrEmail;
    @NotBlank
    private String password;
}
