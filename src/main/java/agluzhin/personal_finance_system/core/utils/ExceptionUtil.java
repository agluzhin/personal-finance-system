package agluzhin.personal_finance_system.core.utils;

import jakarta.security.auth.message.AuthException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

/**
 * Данный класс является утилитой для обработки исключений.
 */
@ControllerAdvice
public class ExceptionUtil {
    // Логгер, используемый для вывода в консоль информации о работе класса "ExceptionUtil".
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionUtil.class);

    /**
     * Метод обработки исключений, связанных с передачей клиентом некорректных данных в запросе.
     * @param ex экземпляр класса "IllegalArgumentException".Ы
     * @return экземпляр класса "ResponseEntity" с кодом ответа 400 и соответствующим сообщением (задано в throw new ...).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        LOG.info(" ===== IllegalArgumentException WAS HANDLED =====");

        return ResponseUtil.generateErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
    }

    /**
     * Метод обработки исключений, связанных с передачей клиентом отсутствующих в хранилище данных в запросе.
     * @param ex экземпляр класса "NoSuchElementException".
     * @return экземпляр класса "ResponseEntity" с кодом 404 и соответствующим сообщением (задано в throw new ...).
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException ex) {
        LOG.info(" ===== NoSuchElementException WAS HANDLED =====");

        return ResponseUtil.generateErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }

    /**
     * Метод обработки исключений, связанных с передачей клиентом неавторизованных запросов.
     * @param ex экземпляр класса "AuthException".
     * @return экземпляр класса "ResponseEntity" с кодом 401 и соответствующим сообщением (задано в throw new ...).
     */
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> handleAuthException(AuthException ex) {
        LOG.info(" ===== AuthException WAS HANDLED =====");

        return ResponseUtil.generateErrorResponse(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage()
        );
    }

    /**
     * Метод обработки исключений, связанных с чем-то "необработанным".
     * @param ex экземпляр класса "Exception".
     * @return экземпляр класса "ResponseEntity" с кодом 500 и соответствующим сообщением.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnknownException(Exception ex) {
        LOG.info(" ===== UnknownException WAS HANDLED =====");
        String loggingMessage = String.format(
                "Error message: '%s'",
                ex.getMessage()
        );
        LOG.info(loggingMessage);

        return ResponseUtil.generateErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                String.format("unchecked error: %s", ex.getMessage())
        );
    }
}
