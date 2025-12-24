package bed.hoc.exercice_hoc.user.dto;

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
public class UserDTOGet {

    private Integer id;
    private String username;
    private String name;
    private String firstname;
    private String email;

}
