package bed.hoc.exercice_hoc.user.mapper;

import bed.hoc.exercice_hoc.user.dto.UserDTOCreate;
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

    }

    @Test
    void updateEntityFromDTO() {

    }
}