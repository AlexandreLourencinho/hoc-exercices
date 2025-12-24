package bed.hoc.exercice_hoc.user.repository;

import bed.hoc.exercice_hoc.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByNameAndFirstname(String name, String firstname);

    Optional<UserEntity> findByUsernameOrEmail(String usernameOrEmail, String mailOrUsername);

}
