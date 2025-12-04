package agluzhin.personal_finance_system.core.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Данный класс является утилитой для генерации успешных/ошибочных ответов от сервера.
 */
public class ResponseUtil {

    /**
     * Метод генерации ответа от сервера в случае возникновения какого-либо исключения.
     * @param httpStatus флаг enum'а HttpStatus;
     * @param message соответствующее сообщение об ошибке.
     * @return ответ сервера.
     */
    public static ResponseEntity<?> generateErrorResponse(HttpStatus httpStatus, String message) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("code", httpStatus.value());
        responseBody.put("status", httpStatus.name());
        responseBody.put("message", message);
        return ResponseEntity.status(httpStatus).body(responseBody);
    }

    /**
     * Метод генерации ответа от сервера в случае успеха.
     * @param httpStatus флаг enum'а HttpStatus;
     * @param message соответствующее сообщение об успешности запроса;
     * @param objectType тип возвращаемого объекта;
     * @param responseObject возвращаемый объект;
     * @return ответ сервера.
     */
    public static ResponseEntity<?> generateSuccessResponse(HttpStatus httpStatus, String message, String objectType, Object responseObject) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("code", httpStatus.value());
        responseBody.put("status", httpStatus.name());
        responseBody.put("message", message);
        responseBody.put(objectType, responseObject);
        return ResponseEntity.status(httpStatus).body(responseBody);
    }
}
