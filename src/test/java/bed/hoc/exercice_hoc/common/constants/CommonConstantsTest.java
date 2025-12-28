package bed.hoc.exercice_hoc.common.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonConstantsTest {

    @Test
    void throwUtilityClassException() {
        assertThrows(IllegalStateException.class, CommonConstants::throwUtilityClassException);
    }

}