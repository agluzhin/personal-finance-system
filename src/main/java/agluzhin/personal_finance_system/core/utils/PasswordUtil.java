package agluzhin.personal_finance_system.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Данный класс является утилитой для работы с хешированием/сравнением пароля "пользователя".
 */
public class PasswordUtil {
    // Логгер, используемый для вывода в консоль информации о работе класса "PasswordUtil".
    private static final Logger LOG = LoggerFactory.getLogger(PasswordUtil.class);
    // Значение стандартного алгоритма хеширования.
    private static final String ALGORITHM = "SHA-256";


    /**
     * Метод хеширования пароля "пользователя" по алгоритму SHA-256.
     * @param rawPassword входное значение пароля "пользователя".
     * @return хеш-значение пароля "пользователя".
     * @throws IllegalStateException исключение, связанное с возникновением любой проблемы в процессе хеширования пароля.
     */
    public static String encode(String rawPassword) throws IllegalStateException {
        try {
            LOG.info(" ===== STARTING PASSWORD HASHING ===== ");
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            byte[] hash = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                String hexByte = Integer.toHexString(0xff & b);
                if (hexByte.length() == 1) hex.append('0');
                hex.append(hexByte);
            }
            LOG.info("SUCCESS: password was hashed");
            return hex.toString();
        } catch (Exception ex) {
            LOG.error("FAILED: password wasn't hashed");
            throw new IllegalStateException("failed to hash password");
        }
    }


    /**
     * Метод сравнения паролей "пользователя".
     * @param rawPassword входное значение пароля "пользователя";
     * @param hashedPassword имеющееся в репозитории (UserRepository) хеш-значение пароля "пользователя";
     * @return значение результата проверки (true/false).
     */
    public static boolean matches(String rawPassword, String hashedPassword) {
        return encode(rawPassword).equals(hashedPassword);
    }
}
