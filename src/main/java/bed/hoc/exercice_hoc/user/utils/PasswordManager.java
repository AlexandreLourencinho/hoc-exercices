package bed.hoc.exercice_hoc.user.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static bed.hoc.exercice_hoc.common.constants.CommonConstants.throwUtilityClassException;

public class PasswordManager {

    private PasswordManager() {
        throwUtilityClassException();
    }

    /*note : this is a basic encryption because it's an exercice project.
    obviously in real cases there's way much stronger authentication / password encryption options like Bcrypt,
    but I didn't want to pollute the project with the spring security configuration that could have led to
    problems for you that aren't useful for you to know now.
    If you master all the exercises there until level 3, you should be able to start investigating and maybe even including
    spring security by yourself and choose another encryption et login options. */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            // Encode en Base64 pour que ce soit lisible / stockable en DB
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur lors du hash du mot de passe", e); // could be a custom exception but have no idea here
        }
    }

    public static boolean verifyPassword(String rawPassword, String hashedPassword) {
        return hashPassword(rawPassword).equals(hashedPassword);
    }

}
