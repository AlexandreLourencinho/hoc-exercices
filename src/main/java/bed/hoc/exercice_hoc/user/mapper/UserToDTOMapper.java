package bed.hoc.exercice_hoc.user.mapper;

import bed.hoc.exercice_hoc.user.dto.UserDTOCreate;
import bed.hoc.exercice_hoc.user.dto.UserDTOGet;
import bed.hoc.exercice_hoc.user.dto.UserDTOUpdate;
import bed.hoc.exercice_hoc.user.entity.UserEntity;
import bed.hoc.exercice_hoc.user.utils.PasswordManager;

import static bed.hoc.exercice_hoc.common.constants.CommonConstants.throwUtilityClassException;

public class UserToDTOMapper {

    private UserToDTOMapper() {
        throwUtilityClassException();
    }

    public static UserEntity getEntityFromDTOCreate(UserDTOCreate dto) {
        return new UserEntity(null, dto.getUsername(), dto.getName(), dto.getFirstname(), dto.getEmail(), PasswordManager.hashPassword(dto.getPassword()));
    }

    public static UserDTOGet getDTOGetFromEntity(UserEntity entity) {
        return null;
    }

    public static void updateEntityFromDTO(UserEntity entity, UserDTOUpdate dto) {
        //TODO
    }

}
