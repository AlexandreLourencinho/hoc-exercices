package bed.hoc.exercice_hoc.user.controller;

import bed.hoc.exercice_hoc.user.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerAdviceTest {

    private UserControllerAdvice advice;

    @BeforeEach
    void setUp() {
        this.advice = new UserControllerAdvice();
    }

    @Test
    void handleUserNotFoundError() {
        var unfex = new UserNotFoundException();

        var result = advice.handleUserNotFoundError(unfex);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("User not found in database", result.getBody());
    }

    @Test
    void handleInvalidCredentialException() {
        var icex = new InvalidCredentialException();

        var result = advice.handleInvalidCredentialException(icex);

        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
        assertEquals("The password is incorrect", result.getBody());
    }

    @Test
    void handleEmailAlreadyExistsException() {
        var eaeex = new EmailAlreadyExistsException("boomed email");

        var result = advice.handleEmailAlreadyExistsException(eaeex);

        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertEquals("boomed email", result.getBody());
    }

    @Test
    void handleNameAndFirstnameAlreadyExistsException() {
        var nafaeex = new NameAndFirstnameAlreadyExistsException("boomed name and firstname");

        var result = advice.handleNameAndFirstnameAlreadyExistsException(nafaeex);

        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertEquals("boomed name and firstname", result.getBody());
    }

    @Test
    void handleUsernameAlreadyTakenException() {
        var uatex = new UsernameAlreadyTakenException("boomed username");

        var result = advice.handleUsernameAlreadyTakenException(uatex);

        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertEquals("boomed username", result.getBody());
    }

}