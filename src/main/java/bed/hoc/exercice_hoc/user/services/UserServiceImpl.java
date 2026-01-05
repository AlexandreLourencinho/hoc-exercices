package bed.hoc.exercice_hoc.user.services;

import bed.hoc.exercice_hoc.user.dto.UserDTOCreate;
import bed.hoc.exercice_hoc.user.dto.UserDTOGet;
import bed.hoc.exercice_hoc.user.dto.UserDTOLogin;
import bed.hoc.exercice_hoc.user.dto.UserDTOUpdate;
import bed.hoc.exercice_hoc.user.entity.UserEntity;
import bed.hoc.exercice_hoc.user.exceptions.*;
import bed.hoc.exercice_hoc.user.mapper.UserToDTOMapper;
import bed.hoc.exercice_hoc.user.model.UserIdentity;
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
    public static final String CREATE_NEW = "create new";
    public static final String UPDATE = "update";

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTOGet loginUser(UserDTOLogin dto) {
        var entity = this.userRepository.findByUsernameOrEmail(dto.getUsernameOrEmail(), dto.getUsernameOrEmail()).orElseThrow(() -> {
            log.error("user wasn't retrieved in db while trying to log in");
            return new UserNotFoundException(String.format("User %s was not found", dto.getUsernameOrEmail()));
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

    /**
     * business method used in other services
     * @param id the id of user to be found
     * @return The {@link UserEntity} retrieved, of a {@link UserNotFoundException} otherwise
     */
    @Override
    public UserEntity getUserEntity(int id) {
        return this.userRepository.findById(id).orElseThrow(() -> {
            log.error("the user wasn't retrieved from DB");
            return new UserNotFoundException(String.format("User with id %s was not found.", id));
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
        this.checkDuplicateFields(new UserIdentity(null,
                dto.getEmail(),
                dto.getUsername(),
                dto.getName(),
                dto.getFirstname()), CREATE_NEW);
        var user = UserToDTOMapper.getEntityFromDTOCreate(dto);
        this.userRepository.save(user);
        return UserToDTOMapper.getDTOGetFromEntity(user);
    }

    @Override
    public UserDTOGet updateUser(UserDTOUpdate dto) {
        this.checkDuplicateFields(new UserIdentity(dto.getId(),
                dto.getEmail(),
                dto.getUsername(),
                dto.getName(),
                dto.getFirstname()), UPDATE);
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

    /**
     * check for potential duplicate fields while trying to update or create a user
     * @param identity a record {@link UserIdentity} containing the needed properties for the checks
     * @param action for the logs, assessing in the message the error happens during a create or an update
     * @throws EmailAlreadyExistsException if the email already exists for a user
     * @throws NameAndFirstnameAlreadyExistsException if the couple name and firstname is already taken
     * @throws UsernameAlreadyTakenException if the username is already taken
     */
    private void checkDuplicateFields(UserIdentity identity, String action) {
        // TODO implement the duplicate check using the provided UserIdentity
    }

}
