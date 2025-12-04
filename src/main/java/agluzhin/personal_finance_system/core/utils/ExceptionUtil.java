package agluzhin.personal_finance_system.core.utils;

import jakarta.security.auth.message.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Данный класс является утилитой для обработки исключений.
 */
@ControllerAdvice
public class ExceptionUtil {

    /**
     * Метод обработки исключения, возникающего при передаче клиентом пустого тела запроса.
     * @param ex объект класса HttpMessageNotReadableException.
     * @return ответ с кодом 400 и соответствующим сообщением.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleEmptyBody(HttpMessageNotReadableException ex) {
        return ResponseUtil.generateErrorResponse(
                HttpStatus.BAD_REQUEST,
                "request body can not be empty."
        );
    }

    /**
     * Метод обработки исключений, возникающих при передаче клиентом некорректных значений полей тела или параметров запроса.
     * @param ex объект класса IllegalArgumentException.
     * @return ответ с кодом 400 и соответствующим сообщением (заданным в throw new ...).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseUtil.generateErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
    }

    /**
     * Метод обработки исключений, возникающих при передаче клиентом отсутствующих в InMemoryDataStorage значений полей тела или параметров запроса.
     * @param ex объект класса NoSuchElementException.
     * @return ответ с кодом 404 и соответствующим сообщением (заданным в throw new ...).
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseUtil.generateErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }


    /**
     * Метод обработки исключений, возникающих при передаче клиентом неавторизованных запросов.
     * @param ex объект класса AuthException.
     * @return ответ с кодом 401 и соответствующим сообщением (заданным в throw new ...).
     */
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> handleAuthException(AuthException ex) {
        return ResponseUtil.generateErrorResponse(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage()
        );
    }

    /**
     * Метод обработки исключений, возникающих неожиданно (те, что не были обработаны).
     * @param ex объект класса Exception.
     * @return ответ с кодом 500 и соответствующим сообщением.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnknownException(Exception ex) {
        return ResponseUtil.generateErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                String.format("error: %s", ex.getMessage())
        );
    }
}
