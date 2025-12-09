package agluzhin.personal_finance_system.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Данный класс является утилитой для генерации успешных/ошибочных ответов от сервера.
 */
public class ResponseUtil {
    // Логгер, используемый для вывода в консоль информации о работе класса "ResponseUtil".
    private static final Logger LOG = LoggerFactory.getLogger(ResponseUtil.class);

    /**
     * Метод генерации ответа от сервера в случае возникновения любого исключения.
     * @param httpStatus флаг enum'а "HttpStatus";
     * @param message соответствующее сообщение об ошибке.
     * @return экземпляр класса "ResponseEntity" с соответствующими кодом и сообщением об ошибке.
     */
    public static ResponseEntity<?> generateErrorResponse(HttpStatus httpStatus, String message) {
        LOG.info(" ===== STARTING ERROR RESPONSE GENERATING ===== ");
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("code", httpStatus.value());
        responseBody.put("status", httpStatus.name());
        responseBody.put("message", message);
        LOG.info("SUCCESS: error response was generated");
        return ResponseEntity.status(httpStatus).body(responseBody);
    }

    /**
     * Метод генерации ответа от сервера в случае успеха.
     * @param httpStatus флаг enum'а "HttpStatus";
     * @param message соответствующее сообщение об успешности запроса;
     * @param objectType тип возвращаемого объекта;
     * @param responseObject возвращаемый объект.
     * @return экземпляр класса "ResponseEntity" с соответствующими кодом и объектом.
     */
    public static ResponseEntity<?> generateSuccessResponse(HttpStatus httpStatus, String message, String objectType, Object responseObject) {
        LOG.info(" ===== STARTING SUCCESS RESPONSE GENERATING ===== ");
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("code", httpStatus.value());
        responseBody.put("status", httpStatus.name());
        responseBody.put("message", message);
        responseBody.put(objectType, responseObject);
        LOG.info("SUCCESS: success response was generated");
        return ResponseEntity.status(httpStatus).body(responseBody);
    }
}
