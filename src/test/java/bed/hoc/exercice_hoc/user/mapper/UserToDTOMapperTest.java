package bed.hoc.exercice_hoc.user.mapper;

import bed.hoc.exercice_hoc.user.dto.UserDTOCreate;
import bed.hoc.exercice_hoc.user.dto.UserDTOUpdate;
import bed.hoc.exercice_hoc.user.entity.UserEntity;
import bed.hoc.exercice_hoc.user.utils.PasswordManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserToDTOMapperTest {

    @Test
    void getEntityFromDTOCreate() {
        var dtoCreate = new UserDTOCreate("username", "name", "firstname", "aaa@aaa.fr", "1234");

        var result = UserToDTOMapper.getEntityFromDTOCreate(dtoCreate);

        assertNull(result.getId());
        assertEquals(result.getEmail(), dtoCreate.getEmail());
        assertEquals(result.getFirstname(), dtoCreate.getFirstname());
        assertEquals(result.getName(), dtoCreate.getName());
        assertEquals(result.getUsername(), dtoCreate.getUsername());
        assertEquals(result.getPassword(), PasswordManager.hashPassword(dtoCreate.getPassword()));
    }

    @Test
    void getDTOGetFromEntity() {
        var entity = new UserEntity(1, "username", "name", "firstname", "aaa@aaa.fr", "Hashed1234");

        var result = UserToDTOMapper.getDTOGetFromEntity(entity);

        assertEquals(entity.getUsername(), result.getUsername());
        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getFirstname(), result.getFirstname());
        assertEquals(entity.getName(), result.getName());
        assertEquals(entity.getEmail(), result.getEmail());
    }

    @Test
    void updateEntityFromDTO() {
        var entity = new UserEntity(1, "username", "name", "firstname", "aaa@aaa.fr", "Hashed1234");
        var dtoUpdate = new UserDTOUpdate(1, "username2", "name2", "firstname2", "aaa@aaa.fr", null);

        UserToDTOMapper.updateEntityFromDTO(entity, dtoUpdate);

        assertEquals(dtoUpdate.getId(), entity.getId());
        assertEquals(dtoUpdate.getName(), entity.getName());
        assertEquals(dtoUpdate.getUsername(), entity.getUsername());
        assertEquals(dtoUpdate.getFirstname(), entity.getFirstname());
        assertEquals(dtoUpdate.getEmail(), entity.getEmail());
    }
}