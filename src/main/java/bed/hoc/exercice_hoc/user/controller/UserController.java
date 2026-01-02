package bed.hoc.exercice_hoc.user.controller;

import bed.hoc.exercice_hoc.user.dto.UserDTOCreate;
import bed.hoc.exercice_hoc.user.dto.UserDTOGet;
import bed.hoc.exercice_hoc.user.dto.UserDTOLogin;
import bed.hoc.exercice_hoc.user.dto.UserDTOUpdate;
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
    public ResponseEntity<UserDTOGet> logUser(@RequestBody @Valid UserDTOLogin dto) {
        return ResponseEntity.ok(this.userService.loginUser(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTOGet> getUser(@PathVariable int id) {
        return ResponseEntity.ok().body(this.userService.getUser(id));
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
    public ResponseEntity<UserDTOGet> saveUser(@RequestBody @Valid UserDTOCreate dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.saveUser(dto));
    }

    @PutMapping
    public ResponseEntity<UserDTOGet> updateUser(@RequestBody @Valid UserDTOUpdate dto) {
        return ResponseEntity.ok().body(this.userService.updateUser(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        this.userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteUsers(@RequestParam List<Integer> ids) {
        this.userService.deleteUsers(ids);
        return ResponseEntity.noContent().build();
    }

}
