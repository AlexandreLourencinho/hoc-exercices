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
        var entity = this.userRepository.findByUsernameOrEmail(dto.getUsernameOrEmail(), dto.getUsernameOrEmail()).orElseThrow(() -> {
            log.error("user wasn't retrieved in db while trying to log in");
            return new UserNotFoundException();
        });
        if (!PasswordManager.verifyPassword(dto.getPassword(), entity.getPassword())) {
            throw new InvalidCredentialException();
        }

        return UserToDTOMapper.getDTOGetFromEntity(entity);
    }

    @Override
    public UserDTOGet getUser(int id) {
        return UserToDTOMapper.getDTOGetFromEntity(this.getUserEntity(id));
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
        return this.userRepository.findAll().stream().map(UserToDTOMapper::getDTOGetFromEntity).toList();
    }

    @Override
    public List<UserDTOGet> getSetOfUsers(List<Integer> ids) {
        return this.userRepository.findAllById(ids).stream().map(UserToDTOMapper::getDTOGetFromEntity).toList();
    }

    @Override
    public UserDTOGet saveUser(UserDTOCreate dto) {
        this.checkDuplicateFields(null, dto);
        var user = UserToDTOMapper.getEntityFromDTOCreate(dto);
        this.userRepository.save(user);
        return UserToDTOMapper.getDTOGetFromEntity(user);
    }

    @Override
    public UserDTOGet updateUser(UserDTOUpdate dto) {
        this.checkDuplicateFields(dto, null);
        var user = this.getUserEntity(dto.getId());
        UserToDTOMapper.updateEntityFromDTO(user, dto);
        this.userRepository.save(user);
        return UserToDTOMapper.getDTOGetFromEntity(user);
    }

    @Override
    public void deleteUser(int id) {
        var user = this.getUserEntity(id);
        this.userRepository.delete(user);
    }

    @Override
    public void deleteUsers(List<Integer> ids) {
        this.userRepository.deleteAllById(ids);
    }

    private void checkDuplicateFields(UserDTOUpdate dtoUpdate, UserDTOCreate dtoCreate) {
        //TODO here you need to use only one method without code duplication to check if the create OR the update is valid.
        // you should have no complexity alert from sonar or intellij and no code duplication here.
        // install the sonar plugin to be sure it's complexity isn't too high.
    }

}
