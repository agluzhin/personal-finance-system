package agluzhin.personal_finance_system.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ParseException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Данный класс является утилитой для работы с хешированием/сравнением паролей.
 */
public class PasswordUtil {
    // Логгер, используемый для вывода в консоль информации о состоянии приложения.
    private static final Logger LOG = LoggerFactory.getLogger(PasswordUtil.class);
    // Значение стандартного алгоритма хеширования.
    private static final String ALGORITHM = "SHA-256";


    /**
     * Метод для хеширования пароля.
     * @param rawPassword значение пароля, переданное клиентом при создании пользователя;
     * @return хеш пароля.
     * @throws IllegalStateException исключение в случае любых проблем с хешированием пароля.
     */
    public static String encode(String rawPassword) throws IllegalStateException {
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            byte[] hash = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                String hexByte = Integer.toHexString(0xff & b);
                if (hexByte.length() == 1) hex.append('0');
                hex.append(hexByte);
            }
            return hex.toString();
        } catch (Exception ex) {
            throw new IllegalStateException("failed to hash password");
        }
    }


    /**
     * Метод сравнения паролей.
     * @param rawPassword значение пароля, переданное клиентом при создании пользователя;
     * @param hashedPassword значения хеша пароля, хранящегося в InMemoryDataStorage;
     * @return значение результата проверки true/false.
     * @throws IllegalStateException исключение в случае любых проблем с хешированием пароля.
     */
    public static boolean matches(String rawPassword, String hashedPassword) throws IllegalStateException {
        return encode(rawPassword).equals(hashedPassword);
    }
}
