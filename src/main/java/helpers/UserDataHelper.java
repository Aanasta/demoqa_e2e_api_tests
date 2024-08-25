package helpers;

import org.apache.commons.lang3.RandomStringUtils;

public class UserDataHelper {

    private static final String RANDOM_SPECIAL_CHARACTERS = "!@#$%";

    public static String generateRandomUsername() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    public static String generateRandomPassword() {
        String password = RandomStringUtils.randomAlphabetic(3).toUpperCase()
                + RandomStringUtils.randomAlphabetic(3).toLowerCase()
                + RandomStringUtils.randomNumeric(1)
                + RandomStringUtils.random(1, RANDOM_SPECIAL_CHARACTERS);
        return password;
    }
}
