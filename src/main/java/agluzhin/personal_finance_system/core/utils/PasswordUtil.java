package agluzhin.personal_finance_system.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class PasswordUtil {
    private static final Logger LOG = LoggerFactory.getLogger(PasswordUtil.class);
    private static final String ALGORITHM = "SHA-256";

    public static String encode(String rawPassword) {
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
            LOG.error("ERROR WHILE HASHING PASSWORD: {}", ex.getMessage());
            return "";
        }
    }

    public static boolean matches(String rawPassword, String hashedPassword) {
        return encode(rawPassword).equals(hashedPassword);
    }
}
