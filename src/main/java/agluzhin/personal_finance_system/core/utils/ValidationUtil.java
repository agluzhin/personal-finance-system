package agluzhin.personal_finance_system.core.utils;

import agluzhin.personal_finance_system.core.entities.user.User;
import agluzhin.personal_finance_system.core.entities.wallet.Wallet;
import agluzhin.personal_finance_system.core.repositories.UserRepository;
import agluzhin.personal_finance_system.core.repositories.WalletRepository;

import jakarta.security.auth.message.AuthException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

/**
 * Данный класс является утилитой для валидации всех видов данных.
 */
public class ValidationUtil {
    // Логгер, используемый для вывода в консоль информации о работе класса "ValidationUtil".
    private static final Logger LOG = LoggerFactory.getLogger(ValidationUtil.class);

    // Паттерн, используемый для уникальных идентификаторов (UUID).
    private static final Pattern UUID_PATTERN = Pattern.compile(
            "^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$"
    );

    // Глобальные типы категорий - "income"/"expenditure".
    private static final String[] CATEGORY_TYPES = {"income", "expenditure"};

    /**
     * Метод требования полей строкового типа быть не равными "null" и не являющимися "empty".
     *
     * @param entityFieldName  название входного параметра для его идентификации (н-р, id, name, type и т.д.);
     * @param entityFieldValue значение входного параметра, которое необходимо проверить;
     * @throws IllegalArgumentException исключение, связанное с некорректностью значения входного параметра.
     */
    public static void requireNonNullOrEmpty(String entityFieldName, String entityFieldValue)
            throws IllegalArgumentException {
        LOG.info(" ===== STARTING NON NULL OR EMPTY CHECK ===== ");

        if (entityFieldValue == null || entityFieldValue.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    String.format(
                            "field '%s' can not be null or empty",
                            entityFieldName
                    )
            );
        }

        String loggingMessage = String.format(
                "SUCCESS - field '%s' was passed 'NonNullOrEmpty' requirement",
                entityFieldName
        );
        LOG.info(loggingMessage);
    }

    /**
     * Метод требования полей объектного типа быть не равными "null".
     *
     * @param entityFieldName  название входного параметра для его идентификации (н-р, isActive);
     * @param entityFieldValue значение входного параметра, которое необходимо проверить;
     * @throws IllegalArgumentException исключение, связанное с некорректностью значения входного параметра.
     */
    public static void requireNonNull(String entityFieldName, Object entityFieldValue)
            throws IllegalArgumentException {
        LOG.info(" ===== STARTING NON NULL CHECK ===== ");

        if (entityFieldValue == null) {
            throw new IllegalArgumentException(
                    String.format(
                            "field '%s' can not be null",
                            entityFieldName
                    )
            );
        }

        String loggingMessage = String.format(
                "SUCCESS - field '%s' was passed 'NonNull' requirement",
                entityFieldName
        );
        LOG.info(loggingMessage);
    }

    /**
     * Метод требования наличия "пользователя" в репозитории (UserRepository) по параметру "id".
     *
     * @param id значение уникального идентификатора "пользователя".
     * @throws NoSuchElementException исключение, связанное с отсутствием "пользователя" в репозитории (UserRepository).
     */
    public static void requireExistUserById(String id)
            throws NoSuchElementException {
        LOG.info(" ===== STARTING USER EXIST BY ID CHECK ===== ");

        if (!UserRepository.getAllUsers().containsKey(id)) {
            throw new NoSuchElementException(
                    String.format(
                            "user with id '%s' not found",
                            id
                    )
            );
        }

        LOG.info("SUCCESS - field 'id' was passed 'ExistUserById' requirement");
    }

    /**
     * Метод требования наличия "пользователя" в репозитории (UserRepository) по параметру "login".
     *
     * @param login значение логина "пользователя";
     * @throws NoSuchElementException исключение, связанное с отсутствием "пользователя" в репозитории (UserRepository).
     */
    public static void requireExistUserByLogin(String login)
            throws NoSuchElementException {
        LOG.info(" ===== STARTING USER EXIST BY LOGIN CHECK ===== ");

        boolean isExist = false;
        for (User user : UserRepository.getAllUsers().values()) {
            if (user.getLogin().equals(login)) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            throw new NoSuchElementException(
                    String.format(
                            "user with login '%s' not found",
                            login
                    )
            );
        }

        LOG.info("SUCCESS - field 'login' was passed 'ExistUserByLogin' requirement");
    }

    /**
     * Метод требования отсутствия "пользователя" в репозитории (UserRepository) по параметру "login".
     *
     * @param login значение логина "пользователя";
     * @throws IllegalArgumentException исключение, связанное с наличием "пользователя" в репозитории (UserRepository).
     */
    public static void requireNonExistUserByLogin(String login)
            throws IllegalArgumentException {
        LOG.info(" ===== STARTING USER NON EXIST BY LOGIN CHECK ===== ");

        for (User user : UserRepository.getAllUsers().values()) {
            if (user.getLogin().equals(login)) {
                throw new IllegalArgumentException(
                        String.format(
                                "user with login '%s' is already exist",
                                login
                        )
                );
            }
        }

        LOG.info("SUCCESS - field 'login' was passed 'NonExistUserByLogin' requirement");
    }

    /**
     * Метод требования наличия у "пользователя" значения "true" по параметру "статус активности".
     *
     * @param id значение уникального идентификатора "пользователя".
     * @throws IllegalArgumentException исключение, связанное с некорректностью значения входного параметра.
     */
    public static void requireUserIsActiveById(String id)
            throws IllegalArgumentException {
        LOG.info(" ===== STARTING USER IS ACTIVE BY ID CHECK ===== ");

        if (!UserRepository.getUserById(id).getIsActive()) {
            throw new IllegalArgumentException(
                    String.format(
                            "user with id '%s' can not be inactive",
                            id
                    )
            );
        }

        LOG.info("SUCCESS - field 'id' was passed 'UserIsActiveById' requirement");
    }

    /**
     * Метод требования наличия у "пользователя" значения "true" для параметра "статус активности" по параметру "walletId".
     *
     * @param id значение уникального идентификатора "кошелька".
     * @throws NoSuchElementException исключение, связанное с отсутствием "пользователя" в репозитории (UserRepository).
     */
    public static void requireUserIsActiveByWalletId(String id)
            throws IllegalArgumentException {
        LOG.info(" ===== STARTING USER IS ACTIVE BY WALLET ID CHECK ===== ");

        for (User user : UserRepository.getAllUsers().values()) {
            if (user.getWalletId().equals(id)) {
                if (!user.getIsActive()) {
                    throw new IllegalArgumentException(
                            String.format(
                                    "user with id '%s' can not be inactive",
                                    user.getId()
                            )
                    );
                }
                break;
            }
        }

        LOG.info("SUCCESS - field 'id' was passed 'UserIsActiveByWalletId' requirement");
    }

    /**
     * Метод требования наличия у "пользователя" значения "true" для параметра "статус авторизации" по параметру "walletId".
     *
     * @param id значение уникального идентификатора "кошелька".
     * @throws AuthException исключение, связанное с отсутствием авторизации "пользователя".
     */
    public static void requireUserIsAuthorizeByWalletId(String id)
            throws AuthException {
        LOG.info(" ===== STARTING USER IS AUTHORIZE BY WALLET ID CHECK ===== ");

        for (User user : UserRepository.getAllUsers().values()) {
            if (user.getWalletId().equals(id)) {
                if (!user.getIsAuthorize()) {
                    throw new AuthException(
                            String.format(
                                    "user with id '%s' can not be unauthorized",
                                    user.getId()
                            )
                    );
                }
                break;
            }
        }

        LOG.info("SUCCESS - field 'id' was passed 'UserIsAuthorizeByWalletId' requirement");
    }

    /**
     * Метод требования наличия "кошелька" по параметру "id".
     *
     * @param id значение уникального идентификатора "кошелька".
     * @throws NoSuchElementException исключение, связанное с отсутствием "кошелька" в репозитории (WalletRepository).
     */
    public static void requireExistWalletById(String id)
            throws NoSuchElementException {
        LOG.info(" ===== STARTING USER EXIST BY WALLET ID CHECK ===== ");

        if (!WalletRepository.getAllWallets().containsKey(id)) {
            throw new NoSuchElementException(
                    String.format(
                            "wallet with id - '%s' not found",
                            id
                    )
            );
        }

        LOG.info("SUCCESS - field 'id' was passed 'ExistWalletById' requirement");
    }

    /**
     * Метод требования наличия категории в "кошельке" по параметру "name".
     *
     * @param wallet экземпляр класса "Wallet";
     * @param type   значение типа категории "кошелька" ("income"/"expenditure");
     * @param name   значение названия категории "кошелька";
     * @throws NoSuchElementException исключение, связанное с отсутствием категории в "кошельке".
     */
    public static void requireExistCategoryByName(Wallet wallet, String type, String name)
            throws NoSuchElementException {
        LOG.info(" ===== STARTING CATEGORY EXIST BY NAME CHECK ===== ");

        Map<String, Map<String, BigDecimal>> categories = wallet.getCategories();
        if (categories.containsKey(type)) {
            Map<String, BigDecimal> typedCategories = categories.get(type);
            if (!typedCategories.containsKey(name)) {
                throw new NoSuchElementException(
                        String.format(
                                "category with name '%s' in type '%s' not found",
                                name,
                                type
                        )
                );
            }
        } else {
            throw new NoSuchElementException(
                    String.format(
                            "category with name '%s' in type '%s' not found",
                            name,
                            type
                    )
            );
        }

        LOG.info("SUCCESS - field 'name' was passed 'ExistCategoryByName' requirement");
    }

    /**
     * Метод требования наличия бюджета в "кошельке" по параметру "name".
     *
     * @param wallet экземпляр класса "Wallet";
     * @param name   значение названия бюджета "кошелька";
     * @throws NoSuchElementException исключение, связанное с отсутствием бюджета в "кошельке".
     */
    public static void requireExistBudgetByName(Wallet wallet, String name)
            throws NoSuchElementException {
        LOG.info(" ===== STARTING BUDGET EXIST BY NAME CHECK ===== ");

        Map<String, BigDecimal> budgets = wallet.getBudgets();
        if (!budgets.containsKey(name)) {
            throw new NoSuchElementException(
                    String.format(
                            "budget with name '%s' not found",
                            name
                    )
            );
        }

        LOG.info("SUCCESS - field 'name' was passed 'ExistBudgetByName' requirement");
    }

    /**
     * Метод валидации паттерна "UUID".
     *
     * @param entityFieldName  название входного параметра для его идентификации (н-р, id);
     * @param entityFieldValue значение входного параметра, которое необходимо проверить;
     * @throws IllegalArgumentException исключение, связанное с некорректностью значения входного параметра.
     */
    public static void validateUUID(String entityFieldName, String entityFieldValue)
            throws IllegalArgumentException {
        LOG.info(" ===== STARTING UUID FORMAT CHECK ===== ");

        if (!UUID_PATTERN.matcher(entityFieldValue.trim()).matches()) {
            throw new IllegalArgumentException(String.format("invalid UUID format for '%s'", entityFieldName));
        }

        String loggingMessage = String.format(
                "SUCCESS - field '%s' was passed 'UUID format' validation",
                entityFieldName
        );
        LOG.info(loggingMessage);
    }

    /**
     * Метод валидации пароля "пользователя" по параметрам "login" и "password".
     *
     * @param login    значение логина "пользователя";
     * @param password значение пароля "пользователя";
     * @throws IllegalArgumentException исключение, связанное с некорректностью значения входного параметра.
     * @throws IllegalStateException    исключение, связанное с хешированием пароля.
     */
    public static void validatePassword(String login, String password)
            throws IllegalArgumentException, IllegalStateException {
        LOG.info(" ===== STARTING PASSWORD VALUE CHECK ===== ");

        boolean isValid = false;
        for (User user : UserRepository.getAllUsers().values()) {
            if (user.getLogin().equals(login)) {
                if (PasswordUtil.matches(password, user.getPassword())) {
                    isValid = true;
                }
            }
        }
        if (!isValid) {
            throw new IllegalArgumentException(String.format("incorrect password for user with login '%s'", login));
        }

        LOG.info("SUCCESS - field 'userPassword' was passed 'Password' validation");
    }

    /**
     * Метод валидации типа категории "кошелька".
     *
     * @param type значение типа категории "кошелька" ("income"/"expenditure").
     * @throws IllegalArgumentException исключение, связанное с некорректностью значения типа категории "кошелька".
     */
    public static void validateCategoryType(String type)
            throws IllegalArgumentException {
        LOG.info(" ===== STARTING CATEGORY TYPE CHECK ===== ");

        boolean isValid = false;
        for (String categoryType : CATEGORY_TYPES) {
            if (categoryType.equals(type.toLowerCase())) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            throw new IllegalArgumentException(
                    String.format(
                            "invalid category type '%s', it should be 'income' or 'expenditure'",
                            type
                    )
            );
        }

        LOG.info("SUCCESS - field 'type' was passed 'CategoryType' validation");
    }
}
