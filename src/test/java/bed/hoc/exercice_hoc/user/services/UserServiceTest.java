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

    private UserRepository repository;
    private UserService service;
    private UserDTOLogin dtoLogin;
    private UserDTOCreate dtoCreate;
    private UserDTOUpdate dtoUpdate;
    private UserEntity entity;

    @BeforeEach
    void setUp() {
        this.repository = mock(UserRepository.class);
        this.service = new UserServiceImpl(this.repository);
        this.dtoLogin = new UserDTOLogin("username", "1234");
        this.entity = new UserEntity(1, "username", "name", "firstname", "mail@a.fr", PasswordManager.hashPassword("1234"));
        this.dtoCreate = new UserDTOCreate("username", "name", "firstname", "mail@mail.com", "1234");
        this.dtoUpdate = new UserDTOUpdate(1, "username", "name", "firstname", "mail@mail.com", null);
    }

    @Test
    void loginUser() {
        when(this.repository.findByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.of(this.entity));

        var result = this.service.loginUser(this.dtoLogin);

        assertEquals(this.dtoLogin.getUsernameOrEmail(), result.getUsername());
        assertEquals(1, result.getId());
    }

    @Test
    void loginUserWithUserNotFound() {
        when(this.repository.findByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> this.service.loginUser(this.dtoLogin));
    }

    @Test
    void loginUserWithInvalidCred() {
        when(this.repository.findByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.of(this.entity));
        this.dtoLogin.setPassword("4567");

        assertThrows(InvalidCredentialException.class, () -> this.service.loginUser(this.dtoLogin));
    }

    @Test
    void getUser() {
        when(this.repository.findById(anyInt())).thenReturn(Optional.of(this.entity));

        var result = this.service.getUser(99);

        assertEquals(1, result.getId());
    }

    @Test
    void getUserNotFound() {
        when(this.repository.findByUsernameOrEmail(anyString(), anyString())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> this.service.getUser(99));
    }

    @Test
    void getUsers() {
        var list = List.of(this.entity);
        when(this.repository.findAll()).thenReturn(list);

        var result = this.service.getUsers();

        assertEquals(list.getFirst().getId(), result.getFirst().getId());
        assertEquals(list.getFirst().getName(), result.getFirst().getName());
        assertEquals(list.getFirst().getFirstname(), result.getFirst().getFirstname());
        assertEquals(list.getFirst().getUsername(), result.getFirst().getUsername());
        assertEquals(list.getFirst().getEmail(), result.getFirst().getEmail());
    }

    @Test
    void getSetOfUsers() {
        var list = List.of(this.entity);
        when(this.repository.findAllById(anyList())).thenReturn(list);

        var result = this.service.getSetOfUsers(List.of(1));

        assertEquals(list.getFirst().getId(), result.getFirst().getId());
        assertEquals(list.getFirst().getName(), result.getFirst().getName());
        assertEquals(list.getFirst().getFirstname(), result.getFirst().getFirstname());
        assertEquals(list.getFirst().getUsername(), result.getFirst().getUsername());
        assertEquals(list.getFirst().getEmail(), result.getFirst().getEmail());
    }

    @Test
    void saveUser() {
        when(this.repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(this.repository.findByNameAndFirstname(anyString(), anyString())).thenReturn(Optional.empty());
        when(this.repository.findByUsername(anyString())).thenReturn(Optional.empty());

        var result = this.service.saveUser(this.dtoCreate);

        assertEquals(this.dtoCreate.getEmail(), result.getEmail());
        assertEquals(this.dtoCreate.getUsername(), result.getUsername());
        assertEquals(this.dtoCreate.getFirstname(), result.getFirstname());
        assertEquals(this.dtoCreate.getName(), result.getName());
    }

    @Test
    void saveUserWithEmailExists() {
        when(this.repository.findByEmail(anyString())).thenReturn(Optional.of(this.entity));

        assertThrows(EmailAlreadyExistsException.class, () -> this.service.saveUser(this.dtoCreate));
    }

    @Test
    void saveUserWithNameFirstnameExists() {
        when(this.repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(this.repository.findByNameAndFirstname(anyString(), anyString())).thenReturn(Optional.of(this.entity));

        assertThrows(NameAndFirstnameAlreadyExistsException.class, () -> this.service.saveUser(this.dtoCreate));
    }

    @Test
    void saveUserWithUsernameExists() {
        when(this.repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(this.repository.findByNameAndFirstname(anyString(), anyString())).thenReturn(Optional.empty());
        when(this.repository.findByUsername(anyString())).thenReturn(Optional.of(this.entity));

        assertThrows(UsernameAlreadyTakenException.class, () -> this.service.saveUser(this.dtoCreate));
    }

    @Test
    void updateUser() {
        this.entity.setId(2);
        when(this.repository.findById(anyInt())).thenReturn(Optional.of(this.entity));
        when(this.repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(this.repository.findByNameAndFirstname(anyString(), anyString())).thenReturn(Optional.empty());
        when(this.repository.findByUsername(anyString())).thenReturn(Optional.empty());

        var result = this.service.updateUser(this.dtoUpdate);

        assertEquals(this.dtoCreate.getEmail(), result.getEmail());
        assertEquals(this.dtoCreate.getUsername(), result.getUsername());
        assertEquals(this.dtoCreate.getFirstname(), result.getFirstname());
        assertEquals(this.dtoCreate.getName(), result.getName());
    }

    @Test
    void updateUserWithMailExists() {
        this.entity.setId(2);
        when(this.repository.findByEmail(anyString())).thenReturn(Optional.of(this.entity));

        assertThrows(EmailAlreadyExistsException.class, () -> this.service.updateUser(this.dtoUpdate));
    }

    @Test
    void updateUserWithNameFirstnameExists() {
        this.entity.setId(2);
        when(this.repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(this.repository.findByNameAndFirstname(anyString(), anyString())).thenReturn(Optional.of(this.entity));

        assertThrows(NameAndFirstnameAlreadyExistsException.class, () -> this.service.updateUser(this.dtoUpdate));
    }

    @Test
    void updateUserWithUsernameExists() {
        this.entity.setId(2);
        when(this.repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(this.repository.findByNameAndFirstname(anyString(), anyString())).thenReturn(Optional.empty());
        when(this.repository.findByUsername(anyString())).thenReturn(Optional.of(this.entity));

        assertThrows(UsernameAlreadyTakenException.class, () -> this.service.updateUser(this.dtoUpdate));
    }

    @Test
    void deleteUser() {
        when(this.repository.findById(anyInt())).thenReturn(Optional.of(this.entity));
        doNothing().when(this.repository).delete(any(UserEntity.class));

        assertDoesNotThrow(() -> this.service.deleteUser(1));
    }

    @Test
    void deleteUsers() {
        doNothing().when(this.repository).deleteAllById(anyList());

        assertDoesNotThrow(() -> this.service.deleteUsers(List.of(1, 2, 3, 4)));
    }

}