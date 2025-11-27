package agluzhin.personal_finance_system.core.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseUtil {
    public static ResponseEntity<?> generateErrorResponse(HttpStatus httpStatus, String message) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("code", httpStatus.value());
        responseBody.put("status", httpStatus.name());
        responseBody.put("message", message);
        return ResponseEntity.status(httpStatus).body(responseBody);
    }

    public static ResponseEntity<?> generateSuccessResponse(HttpStatus httpStatus, String message, String objectType, Object responseObject) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("code", httpStatus.value());
        responseBody.put("status", httpStatus.name());
        responseBody.put("message", message);
        responseBody.put(objectType, responseObject);
        return ResponseEntity.status(httpStatus).body(responseBody);
    }
}
