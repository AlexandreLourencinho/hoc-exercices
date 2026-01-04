package bed.hoc.exercice_hoc.user.services;

import bed.hoc.exercice_hoc.user.dto.UserDTOCreate;
import bed.hoc.exercice_hoc.user.dto.UserDTOLogin;
import bed.hoc.exercice_hoc.user.dto.UserDTOUpdate;
import bed.hoc.exercice_hoc.user.entity.UserEntity;
import bed.hoc.exercice_hoc.user.exceptions.*;
import bed.hoc.exercice_hoc.user.repository.UserRepository;
import bed.hoc.exercice_hoc.user.utils.PasswordManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService service;


    @BeforeEach
    void setUp() {

    }

    @Test
    void loginUser() {

    }

    @Test
    void loginUserWithUserNotFound() {

    }

    @Test
    void loginUserWithInvalidCred() {

    }

    @Test
    void getUser() {

    }

    @Test
    void getUserNotFound() {

    }

    @Test
    void getUsers() {

    }

    @Test
    void getSetOfUsers() {

    }

    @Test
    void saveUser() {

    }

    @Test
    void saveUserWithEmailExists() {

    }

    @Test
    void saveUserWithNameFirstnameExists() {

    }

    @Test
    void saveUserWithUsernameExists() {

    }

    @Test
    void updateUser() {

    }

    @Test
    void updateUserWithMailExists() {

    }

    @Test
    void updateUserWithNameFirstnameExists() {

    }

    @Test
    void updateUserWithUsernameExists() {

    }

    @Test
    void deleteUser() {

    }

    @Test
    void deleteUsers() {

    }

}