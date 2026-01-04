package bed.hoc.exercice_hoc.user.services;

import bed.hoc.exercice_hoc.user.dto.UserDTOCreate;
import bed.hoc.exercice_hoc.user.dto.UserDTOGet;
import bed.hoc.exercice_hoc.user.dto.UserDTOLogin;
import bed.hoc.exercice_hoc.user.dto.UserDTOUpdate;
import bed.hoc.exercice_hoc.user.entity.UserEntity;
import bed.hoc.exercice_hoc.user.exceptions.*;
import bed.hoc.exercice_hoc.user.mapper.UserToDTOMapper;
import bed.hoc.exercice_hoc.user.repository.UserRepository;
import bed.hoc.exercice_hoc.user.utils.PasswordManager;
import java.util.Collections;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTOGet loginUser(UserDTOLogin dto) {
        return null;
    }

    @Override
    public UserDTOGet getUser(int id) {
        return null;
    }

    @Override
    public UserEntity getUserEntity(int id) {
        return this.userRepository.findById(id).orElseThrow(() -> {
            log.error("the user wasn't retrieved from DB");
            return new UserNotFoundException();
        });
    }

    @Override
    public List<UserDTOGet> getUsers() {
        return Collections.emptyList();
    }

    @Override
    public List<UserDTOGet> getSetOfUsers(List<Integer> ids) {
        return Collections.emptyList();
    }

    @Override
    public UserDTOGet saveUser(UserDTOCreate dto) {
        return null;
    }

    @Override
    public UserDTOGet updateUser(UserDTOUpdate dto) {
        return null;
    }

    @Override
    public void deleteUser(int id) {
        //TODO
    }

    @Override
    public void deleteUsers(List<Integer> ids) {
        //TODO
    }

    private void checkDuplicateFields(UserDTOCreate dto) {
        //TODO
    }

    private void checkDuplicateFields(UserDTOUpdate dto) {
        //TODO
    }

}
