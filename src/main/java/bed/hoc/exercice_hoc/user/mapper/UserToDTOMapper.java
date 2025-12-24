package bed.hoc.exercice_hoc.user.mapper;

import bed.hoc.exercice_hoc.user.dto.UserDTOCreate;
import bed.hoc.exercice_hoc.user.dto.UserDTOGet;
import bed.hoc.exercice_hoc.user.dto.UserDTOUpdate;
import bed.hoc.exercice_hoc.user.entity.UserEntity;
import bed.hoc.exercice_hoc.user.utils.PasswordManager;

public class UserToDTOMapper {

    private UserToDTOMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static UserEntity getEntityFromDTOCreate(UserDTOCreate dto) {
        return new UserEntity(null, dto.getUsername(), dto.getName(), dto.getFirstname(), dto.getEmail(), PasswordManager.hashPassword(dto.getPassword()));
    }

    public static UserDTOGet getDTOGetFromEntity(UserEntity entity) {
        return new UserDTOGet(entity.getId(), entity.getUsername(), entity.getName(), entity.getFirstname(), entity.getEmail());
    }

    public static void updateEntityFromDTO(UserEntity entity, UserDTOUpdate dto) {
        entity.setEmail(dto.getEmail())
                .setFirstname(dto.getFirstname())
                .setName(dto.getName())
                .setUsername(dto.getUsername());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            entity.setPassword(PasswordManager.hashPassword(dto.getPassword()));
        }
    }

}
