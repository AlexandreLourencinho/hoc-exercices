package bed.hoc.exercice_hoc.user.repository;

import bed.hoc.exercice_hoc.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // sets the interface as a repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> { // extends jpa repository to inherite from jpa methods

    //every method defined here follows the JPA language "findBy**Field**"
    // every find method that returns only one results always returns an optional

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByNameAndFirstname(String name, String firstname);

    Optional<UserEntity> findByUsernameOrEmail(String usernameOrEmail, String mailOrUsername);

}
