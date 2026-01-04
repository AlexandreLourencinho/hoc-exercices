package bed.hoc.exercice_hoc.common.constants;

/**
 * this is a common utility class that I used for commodity. you can see some logic abstraction here
 * there isn't much to learn here, it was just more comfortable for me instead of repeating the utility class
 * private constructor throwing logic.
 */
public class CommonConstants {

    public static final String UTILITY_CLASS = "Utility class";

    private CommonConstants() {
        throwUtilityClassException();
    }

    public static void throwUtilityClassException() {
        throw new IllegalStateException(UTILITY_CLASS);
    }

}
