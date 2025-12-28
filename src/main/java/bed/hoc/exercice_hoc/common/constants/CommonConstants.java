package bed.hoc.exercice_hoc.common.constants;

public class CommonConstants {

    public static final String UTILITY_CLASS = "Utility class";

    private CommonConstants() {
        throwUtilityClassException();
    }

    public static void throwUtilityClassException() {
        throw new IllegalStateException(UTILITY_CLASS);
    }

}
