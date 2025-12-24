package bed.hoc.exercice_hoc.user.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordManagerTest {

    @Test
    void hashPassword() {
        var result = PasswordManager.hashPassword("1234");

        assertTrue(PasswordManager.verifyPassword("1234", result));
    }

}