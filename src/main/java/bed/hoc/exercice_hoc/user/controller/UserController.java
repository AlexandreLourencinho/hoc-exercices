package bed.hoc.exercice_hoc.user.controller;

import bed.hoc.exercice_hoc.user.dto.UserDTOCreate;
import bed.hoc.exercice_hoc.user.dto.UserDTOGet;
import bed.hoc.exercice_hoc.user.dto.UserDTOLogin;
import bed.hoc.exercice_hoc.user.dto.UserDTOUpdate;
import bed.hoc.exercice_hoc.user.exceptions.*;
import bed.hoc.exercice_hoc.user.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity logUser(@RequestBody @Valid UserDTOLogin dto) {
        try {
            return ResponseEntity.ok(this.userService.loginUser(dto));
        } catch (UserNotFoundException unfex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfex.getMessage());
        } catch (InvalidCredentialException icex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(icex.getMessage());
        }/* important note here: in a real case, we would just say unhautorized to not expose if the user exists or not
        just because to not give information to a potential malicious connection attempt to know that the user
        he tries to connect with is an actual user in our DB or not.
        I let it like that here as another example of managing exception in the controller, it won't be managed like that
        in further levels of exercices / solutions.
        */
    }

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable int id) {
        try {
            return ResponseEntity.ok().body(this.userService.getUser(id));
        } catch (UserNotFoundException unfex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDTOGet>> getUsersList(@RequestParam(required = false) List<Integer> ids) {
        if (ids != null && !ids.isEmpty()) {
            return ResponseEntity.ok().body(this.userService.getSetOfUsers(ids));
        } else {
            return ResponseEntity.ok().body(this.userService.getUsers());
        }
    }

    @PostMapping
    public ResponseEntity saveUser(@RequestBody @Valid UserDTOCreate dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.saveUser(dto));
        } catch (EmailAlreadyExistsException | NameAndFirstnameAlreadyExistsException |
                 UsernameAlreadyTakenException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity updateUser(@RequestBody @Valid UserDTOUpdate dto) {
        try {
            return ResponseEntity.ok().body(this.userService.updateUser(dto));
        } catch (EmailAlreadyExistsException | NameAndFirstnameAlreadyExistsException |
                 UsernameAlreadyTakenException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable int id) {
        try {
            this.userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException unfex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(unfex.getMessage());
        }

    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteUsers(@RequestParam List<Integer> ids) {
        this.userService.deleteUsers(ids);
        return ResponseEntity.noContent().build();
    }

}
