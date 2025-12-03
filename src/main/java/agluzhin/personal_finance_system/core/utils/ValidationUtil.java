package agluzhin.personal_finance_system.core.utils;

import agluzhin.personal_finance_system.core.entities.user.User;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

public class ValidationUtil {
    private static final Pattern UUID_PATTERN = Pattern.compile(
            "^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$"
    );

    public static void requireNonEmpty(String entityFieldName, String entityFieldValue)
            throws IllegalArgumentException {
        if (entityFieldValue == null || entityFieldValue.trim().isEmpty()) {
            throw new IllegalArgumentException(String.format("field '%s' can not be null or empty", entityFieldName));
        }
    }

    public static void requireNonNull(String entityFieldName, Object entityFieldValue)
            throws IllegalArgumentException {
        if (entityFieldValue == null) {
            throw new IllegalArgumentException(String.format("field '%s' can not be null", entityFieldName));
        }
    }

    public static void validateUUID(String entityFieldName, String entityFieldValue)
            throws IllegalArgumentException {
        if (!UUID_PATTERN.matcher(entityFieldValue).matches()) {
            throw new IllegalArgumentException(String.format("invalid UUID format for '%s'", entityFieldName));
        }
    }

    public static void requireExistUserById(Map<String, User> dataStorage, String userId)
            throws NoSuchElementException {
        if (!dataStorage.containsKey(userId)) {
            throw new NoSuchElementException(String.format("user with id - '%s' not found", userId));
        }
    }

    public static void requireExistUserByLogin(Map<String, User> dataStorage, String userLogin)
            throws NoSuchElementException {
        boolean isExist = false;
        for (User user : dataStorage.values()) {
            if (user.getLogin().equals(userLogin)) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            throw new NoSuchElementException(String.format("user with login - '%s' not found", userLogin));
        }
    }

    public static void requireNonExistUserByLogin(Map<String, User> dataStorage, String userLogin)
            throws IllegalArgumentException {
        for (User user : dataStorage.values()) {
            if (user.getLogin().equals(userLogin)) {
                throw new IllegalArgumentException(String.format("user with login '%s' is already exist", userLogin));
            }
        }
    }

    public static String validatePassword(Map<String, User> dataStorage, String userLogin, String userPassword) {
        for (User user : dataStorage.values()) {
            if (user.getLogin().equals(userLogin)) {
                if (PasswordUtil.matches(userPassword, user.getPassword())) {
                    return user.getUserId();
                }
            }
        }
        throw new IllegalArgumentException(String.format("incorrect password for user with login '%s'", userLogin));
    }
}
