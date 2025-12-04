package agluzhin.personal_finance_system.core.utils;

import agluzhin.personal_finance_system.core.entities.user.User;
import agluzhin.personal_finance_system.core.entities.wallet.Wallet;
import agluzhin.personal_finance_system.core.repositories.InMemoryDataStorage;
import jakarta.security.auth.message.AuthException;

import java.math.BigDecimal;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

/**
 * Данный класс является утилитой для валидации всех видов данных.
 */
public class ValidationUtil {
    // Паттерн, используемый для записи id.
    private static final Pattern UUID_PATTERN = Pattern.compile(
            "^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$"
    );
    // Глобальные типы категорий - "доходы"/"расходы".
    private static final String[] categoryTypes = {"income", "expenditure"};

    /**
     * Метод требования полей строкового типа быть "не пустыми", то есть не равными "null" и не являющимися "empty".
     * @param entityFieldName имя входного параметра для его идентификации (н-р, userId);
     * @param entityFieldValue значение входного параметра, которое необходимо проверить;
     * @throws IllegalArgumentException исключение, связанное со значением входного параметра.
     */
    public static void requireNonEmpty(String entityFieldName, String entityFieldValue)
            throws IllegalArgumentException {
        if (entityFieldValue == null || entityFieldValue.trim().isEmpty()) {
            throw new IllegalArgumentException(String.format("field '%s' can not be null or empty", entityFieldName));
        }
    }

    /**
     * Метод требования полей объектного типа быть "не пустыми", то есть не равными "null".
     * @param entityFieldName имя входного параметра для его идентификации (н-р, isActive);
     * @param entityFieldValue значение входного параметра, которое необходимо проверить;
     * @throws IllegalArgumentException исключение, связанное со значением входного параметра.
     */
    public static void requireNonNull(String entityFieldName, Object entityFieldValue)
            throws IllegalArgumentException {
        if (entityFieldValue == null) {
            throw new IllegalArgumentException(String.format("field '%s' can not be null", entityFieldName));
        }
    }

    /**
     * Метод валидации паттерна UUID.
     * @param entityFieldName имя входного параметра для его идентификации (н-р, userId);
     * @param entityFieldValue значение входного параметра, которое необходимо проверить;
     * @throws IllegalArgumentException исключение, связанное со значением входного параметра.
     */
    public static void validateUUID(String entityFieldName, String entityFieldValue)
            throws IllegalArgumentException {
        if (!UUID_PATTERN.matcher(entityFieldValue).matches()) {
            throw new IllegalArgumentException(String.format("invalid UUID format for '%s'", entityFieldName));
        }
    }


    /**
     * Метод требования наличия сущности "user" в InMemoryDataStorage по параметру "userId".
     * @param dataStorage хранилище пользователей (InMemoryDataStorage.users);
     * @param userId значение параметра "userId";
     * @throws NoSuchElementException исключение, связанное с отсутствием пользователя в хранилище.
     */
    public static void requireExistUserById(Map<String, User> dataStorage, String userId)
            throws NoSuchElementException {
        if (!dataStorage.containsKey(userId)) {
            throw new NoSuchElementException(String.format("user with id - '%s' not found", userId));
        }
    }

    /**
     * Метод требования наличия сущности "user" в InMemoryDataStorage по параметру "login".
     * @param dataStorage хранилище пользователей (InMemoryDataStorage.users);
     * @param userLogin значение параметра "login";
     * @throws NoSuchElementException исключение, связанное с отсутствием пользователя в хранилище.
     */
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

    /**
     * Метод требования отсутствия сущности "user" в InMemoryDataStorage по параметру "login".
     * @param dataStorage хранилище пользователей (InMemoryDataStorage.users);
     * @param userLogin значение параметра "login";
     * @throws IllegalArgumentException исключение, связанное с наличием пользователя в хранилище.
     */
    public static void requireNonExistUserByLogin(Map<String, User> dataStorage, String userLogin)
            throws IllegalArgumentException {
        for (User user : dataStorage.values()) {
            if (user.getLogin().equals(userLogin)) {
                throw new IllegalArgumentException(String.format("user with login '%s' is already exist", userLogin));
            }
        }
    }

    /**
     * Метод валидации пароля.
     * @param dataStorage хранилище пользователей (InMemoryDataStorage.users);
     * @param userLogin значения параметра "login";
     * @param userPassword значение параметра "password";
     * @return значение параметра "userId" для дальнейшей манипуляции;
     * @throws IllegalArgumentException исключение, связанное с некорректным вводом пароля;
     * @throws IllegalStateException исключение, связанное с хешем пароля.
     */
    public static String validatePassword(Map<String, User> dataStorage, String userLogin, String userPassword)
            throws IllegalArgumentException, IllegalStateException {
        for (User user : dataStorage.values()) {
            if (user.getLogin().equals(userLogin)) {
                if (PasswordUtil.matches(userPassword, user.getPassword())) {
                    return user.getUserId();
                }
            }
        }
        throw new IllegalArgumentException(String.format("incorrect password for user with login '%s'", userLogin));
    }

    /**
     * Метод требования пользовательской авторизации.
     * @param dataStorage хранилище пользователей (InMemoryDataStorage.users);
     * @param userId значение параметра "userId";
     * @throws AuthException исключение, связанное со статусом авторизации пользователя.
     */
    public static void requireUserAuthorized(Map<String, User> dataStorage, String userId)
            throws AuthException {
        if (!dataStorage.get(userId).getIsAuthorized()) {
            throw new AuthException(String.format("user with id - '%s' can not be unauthorized", userId));
        }
    }

    /**
     * Метод требования наличия категории в сущности "кошелек" по параметру "categoryName".
     * @param wallet сущность "кошелек";
     * @param categoryType тип категории (из хранилища categoryTypes);
     * @param categoryName значение параметра "categoryName";
     * @throws NoSuchElementException исключение, связанное с отсутствием категории в кошельке.
     */
    public static void requireExistCategoryByName(Wallet wallet, String categoryType, String categoryName)
            throws NoSuchElementException {
        Map<String, Map<String, BigDecimal>> categories = wallet.getCategories();
        if (categories.containsKey(categoryType)) {
            Map<String, BigDecimal> typedCategories = categories.get(categoryType);
            if (!typedCategories.containsKey(categoryName)) {
                throw new NoSuchElementException(String.format("category with name '%s' in type '%s' not found", categoryName, categoryType));
            }
        } else {
            throw new NoSuchElementException(String.format("category with name '%s' in type '%s' not found", categoryName, categoryType));
        }
    }

    /**
     * Метод валидации типа категории.
     * @param categoryType значение параметра "categoryType";
     * @throws IllegalArgumentException исключение, связанное с неизвестным типом категории.
     */
    public static void validateCategoryType(String categoryType)
            throws IllegalArgumentException {
        boolean isValid = false;
        for (String type : categoryTypes) {
            if (type.equals(categoryType.trim().toLowerCase())) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            throw new IllegalArgumentException(String.format("invalid category type - '%s', it should be 'income' or 'expenditure'", categoryType));
        }
    }
}
