package bed.hoc.exercice_hoc.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity // entity sets the class as a persistence entity that will be transcripted in database
@Getter // builds the getter for all fields
@Setter // builds the setter for all fields
@AllArgsConstructor // builds a constructor with all fields
@NoArgsConstructor // builds an empty constructor
@Accessors(chain = true) // allows to chain setter (user.setX().setY().setZ();)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "firstname"})}) // define a unique constraint for a combo of two or more columns
public class UserEntity {

    @Id // sets the field as the primary key of the table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // sets generation as incremental integer
    private Integer id;
    @Column(unique = true) // states that this column couldn't be duplicated
    private String username;
    private String name;
    private String firstname;
    @Column(unique = true) // states that this column couldn't be duplicated
    private String email;
    private String password;

}
