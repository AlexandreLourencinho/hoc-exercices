package bed.hoc.exercice_hoc.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "firstname"})})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String username;
    private String name;
    private String firstname;
    @Column(unique = true)
    private String email;
    private String password;

}
