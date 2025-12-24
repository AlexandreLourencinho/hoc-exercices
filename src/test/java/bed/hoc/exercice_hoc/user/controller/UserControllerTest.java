package bed.hoc.exercice_hoc.user.controller;

import bed.hoc.exercice_hoc.user.dto.UserDTOCreate;
import bed.hoc.exercice_hoc.user.dto.UserDTOGet;
import bed.hoc.exercice_hoc.user.dto.UserDTOLogin;
import bed.hoc.exercice_hoc.user.dto.UserDTOUpdate;
import bed.hoc.exercice_hoc.user.exceptions.*;
import bed.hoc.exercice_hoc.user.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private UserController controller;
    private UserService service;
    private UserDTOGet dtoGet;
    private UserDTOGet dtoGet2;
    private UserDTOLogin dtoLogin;
    private UserDTOCreate dtoCreate;
    private UserDTOUpdate dtoUpdate;

    @BeforeEach
    void setUp() {
        this.service = mock(UserService.class);
        this.controller = new UserController(this.service);
        this.dtoLogin = new UserDTOLogin("myusername", "1234");
        this.dtoGet = new UserDTOGet(1, "myusername", "name", "firstname", "myemail@email.com");
        this.dtoGet2 = new UserDTOGet(2, "myusername2", "name2", "firstname2", "myemail2@email.com");
        this.dtoCreate = new UserDTOCreate("username", "name", "firstname", "aaa@aaa.fr", "1234");
        this.dtoUpdate = new UserDTOUpdate(1, "username", "name", "firstname", "aaa@aaa.fr", "1234");
    }

    @Test
    void logUser() {
        when(this.service.loginUser(any(UserDTOLogin.class))).thenReturn(dtoGet);

        var result = this.controller.logUser(dtoLogin);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(dtoGet, result.getBody());
    }

    @Test
    void logUserWithUserNotFound() {
        doThrow(new UserNotFoundException()).when(this.service).loginUser(any(UserDTOLogin.class));

        var result = this.controller.logUser(dtoLogin);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("User not found in database", result.getBody());
    }

    @Test
    void logUserWithInvalidCred() {
        doThrow(new InvalidCredentialException()).when(this.service).loginUser(any(UserDTOLogin.class));

        var result = this.controller.logUser(dtoLogin);

        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
        assertEquals("The password is incorrect", result.getBody());
    }

    @Test
    void getUser() {
        when(this.service.getUser(any(Integer.class))).thenReturn(dtoGet);

        var result = this.controller.getUser(dtoGet.getId());

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(dtoGet, result.getBody());
    }

    @Test
    void getUserNotFound() {
        doThrow(new UserNotFoundException()).when(this.service).getUser(any(Integer.class));

        var result = this.controller.getUser(1);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("User not found in database", result.getBody());
    }

    @Test
    void getUsersList() {
        var list = Arrays.asList(dtoGet, dtoGet2);
        when(this.service.getUsers()).thenReturn(list);

        var result = this.controller.getUsersList();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(list, result.getBody());
    }

    @Test
    void testGetUsersList() {
        var listOfIds = Arrays.asList(1, 2, 3);
        var list = Arrays.asList(dtoGet, dtoGet2);
        when(this.service.getSetOfUsers(anyList())).thenReturn(list);

        var result = this.controller.getUsersList(listOfIds);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(list, result.getBody());
    }

    @Test
    void saveUser() {
        when(this.service.saveUser(any(UserDTOCreate.class))).thenReturn(dtoGet);

        var result = this.controller.saveUser(dtoCreate);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(this.dtoGet, result.getBody());
    }

    @Test
    void saveUserWithEmailTaken() {
        doThrow(new EmailAlreadyExistsException("exists")).when(this.service).saveUser(any(UserDTOCreate.class));

        var result = this.controller.saveUser(dtoCreate);

        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertEquals("exists", result.getBody());
    }

    @Test
    void saveUserWithNameFirstnameTaken() {
        doThrow(new NameAndFirstnameAlreadyExistsException("exists")).when(this.service).saveUser(any(UserDTOCreate.class));

        var result = this.controller.saveUser(dtoCreate);

        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertEquals("exists", result.getBody());
    }

    @Test
    void saveUserWithUsernameTaken() {
        doThrow(new UsernameAlreadyTakenException("exists")).when(this.service).saveUser(any(UserDTOCreate.class));

        var result = this.controller.saveUser(dtoCreate);

        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertEquals("exists", result.getBody());
    }

    @Test
    void updateUser() {
        when(this.service.updateUser(any(UserDTOUpdate.class))).thenReturn(dtoGet);

        var result = this.controller.updateUser(dtoUpdate);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(this.dtoGet, result.getBody());
    }

    @Test
    void updateUserWithEmailTaken() {
        doThrow(new EmailAlreadyExistsException("exists")).when(this.service).updateUser(any(UserDTOUpdate.class));

        var result = this.controller.updateUser(dtoUpdate);

        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertEquals("exists", result.getBody());
    }

    @Test
    void updateUserWithNameFirstnameTaken() {
        doThrow(new NameAndFirstnameAlreadyExistsException("exists")).when(this.service).updateUser(any(UserDTOUpdate.class));

        var result = this.controller.updateUser(dtoUpdate);

        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertEquals("exists", result.getBody());
    }

    @Test
    void updateUserWithUsernameTaken() {
        doThrow(new UsernameAlreadyTakenException("exists")).when(this.service).updateUser(any(UserDTOUpdate.class));

        var result = this.controller.updateUser(dtoUpdate);

        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertEquals("exists", result.getBody());
    }

    @Test
    void deleteUser() {
        doNothing().when(this.service).deleteUser(any(Integer.class));

        var result = this.controller.deleteUser(1);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    void deleteUserWithUserNotFound() {
        doThrow(new UserNotFoundException()).when(this.service).deleteUser(any(Integer.class));

        var result = this.controller.deleteUser(1);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("User not found in database", result.getBody());
    }

    @Test
    void deleteUsers() {
        doNothing().when(this.service).deleteUsers(anyList());

        var result = this.controller.deleteUsers(List.of(1, 2, 3, 4));

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
}