package bed.hoc.exercice_hoc.user.services;

import bed.hoc.exercice_hoc.user.dto.UserDTOCreate;
import bed.hoc.exercice_hoc.user.dto.UserDTOGet;
import bed.hoc.exercice_hoc.user.dto.UserDTOLogin;
import bed.hoc.exercice_hoc.user.dto.UserDTOUpdate;

import java.util.List;

public interface UserService {

    UserDTOGet loginUser(UserDTOLogin dto);

    UserDTOGet getUser(int id);

    List<UserDTOGet> getUsers();

    List<UserDTOGet> getSetOfUsers(List<Integer> ids);

    UserDTOGet saveUser(UserDTOCreate dto);

    UserDTOGet updateUser(UserDTOUpdate dto);

    void deleteUser(int id);

    void deleteUsers(List<Integer> ids);

}
