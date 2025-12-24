package bed.hoc.exercice_hoc.user.services;

import bed.hoc.exercice_hoc.user.dto.UserDTOCreate;
import bed.hoc.exercice_hoc.user.dto.UserDTOGet;
import bed.hoc.exercice_hoc.user.dto.UserDTOLogin;
import bed.hoc.exercice_hoc.user.dto.UserDTOUpdate;
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
    public static final String CREATE_NEW = "create new";
    public static final String UPDATE = "update";
    private static final String MAIL_ALREADY_EXISTS = "email already exists in db while trying to {} user";
    private static final String COUPLE_NAME_FIRSTNAME_ALREADY_EXISTS = "couple name + firstname already exists in db while trying to {} user";
    private static final String USERNAME_ALREADY_EXISTS = "Username already exists in db while trying to {} user";

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
        return UserToDTOMapper.getDTOGetFromEntity(this.userRepository.findById(id).orElseThrow(() -> {
            log.error("couldn't get the user, the id wasn't found in db");
            return new UserNotFoundException();
        }));
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
        this.checkDuplicateFields(dto);
        var user = UserToDTOMapper.getEntityFromDTOCreate(dto);
        this.userRepository.save(user);
        return UserToDTOMapper.getDTOGetFromEntity(user);
    }

    @Override
    public UserDTOGet updateUser(UserDTOUpdate dto) {
        this.checkDuplicateFields(dto);
        var user = this.userRepository.findById(dto.getId()).orElseThrow(() -> {
            log.error("the user wasn't retrieved from DB");
            return new UserNotFoundException();
        });
        UserToDTOMapper.updateEntityFromDTO(user, dto);
        this.userRepository.save(user);
        return UserToDTOMapper.getDTOGetFromEntity(user);
    }

    @Override
    public void deleteUser(int id) {
        var user = this.userRepository.findById(id).orElseThrow(() -> {
            log.error("couldn't delete the user since the id wasn't found in DB");
            return new UserNotFoundException();
        });
        this.userRepository.delete(user);
    }

    @Override
    public void deleteUsers(List<Integer> ids) {
        this.userRepository.deleteAllById(ids);
    }

    private void checkDuplicateFields(UserDTOCreate dto) {
        var entityFromMail = this.userRepository.findByEmail(dto.getEmail());
        if (entityFromMail.isPresent()) {
            log.error(MAIL_ALREADY_EXISTS, CREATE_NEW);
            throw new EmailAlreadyExistsException("this mail is already used by another account");
        }
        var entityFromName = this.userRepository.findByNameAndFirstname(dto.getName(), dto.getFirstname());
        if (entityFromName.isPresent()) {
            log.error(COUPLE_NAME_FIRSTNAME_ALREADY_EXISTS, CREATE_NEW);
            throw new NameAndFirstnameAlreadyExistsException("The couple name + firstname is already used by another account");
        }
        var entityFromUsername = this.userRepository.findByUsername(dto.getUsername());
        if (entityFromUsername.isPresent()) {
            log.error(USERNAME_ALREADY_EXISTS, CREATE_NEW);
            throw new UsernameAlreadyTakenException("This username is already in use.");
        }
    }

    private void checkDuplicateFields(UserDTOUpdate dto) {
        var entityFromMail = this.userRepository.findByEmail(dto.getEmail());
        if (entityFromMail.isPresent() && !entityFromMail.get().getId().equals(dto.getId())) {
            log.error(MAIL_ALREADY_EXISTS, UPDATE);
            throw new EmailAlreadyExistsException("this mail is already used by another account");
        }
        var entityFromName = this.userRepository.findByNameAndFirstname(dto.getName(), dto.getFirstname());
        if (entityFromName.isPresent() && !entityFromName.get().getId().equals(dto.getId())) {
            log.error(COUPLE_NAME_FIRSTNAME_ALREADY_EXISTS, UPDATE);
            throw new NameAndFirstnameAlreadyExistsException("The couple name + firstname is already used by another account");
        }
        var entityFromUsername = this.userRepository.findByUsername(dto.getUsername());
        if (entityFromUsername.isPresent() && !entityFromUsername.get().getId().equals(dto.getId())) {
            log.error(USERNAME_ALREADY_EXISTS, UPDATE);
            throw new UsernameAlreadyTakenException("This username is already in use.");
        }
    }

}
