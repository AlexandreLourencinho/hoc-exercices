package bed.hoc.exercice_hoc.user.controller;

import bed.hoc.exercice_hoc.user.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = UserController.class)
public class UserControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundError(UserNotFoundException unfex) {
        log.error("user in database wasn't retrieved", unfex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfex.getMessage());
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<String> handleInvalidCredentialException(InvalidCredentialException icex) {
        log.error("credential were wrong while attempting to log in", icex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(icex.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExistsException(EmailAlreadyExistsException eaeex) {
        log.error("the email used was already existing in database", eaeex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(eaeex.getMessage());
    }

    @ExceptionHandler(NameAndFirstnameAlreadyExistsException.class)
    public ResponseEntity<String> handleNameAndFirstnameAlreadyExistsException(NameAndFirstnameAlreadyExistsException nafaeex) {
        log.error("the couple name and firstname was already existing in database", nafaeex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(nafaeex.getMessage());
    }

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    public ResponseEntity<String> handleUsernameAlreadyTakenException(UsernameAlreadyTakenException uatex) {
        log.error("the username was already existing in database", uatex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(uatex.getMessage());
    }

}
