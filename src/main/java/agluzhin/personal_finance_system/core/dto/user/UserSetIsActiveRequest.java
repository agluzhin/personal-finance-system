package agluzhin.personal_finance_system.core.dto.user;

/**
 * DTO для запроса на изменение значения по полю статус активности (isActive).
 * @param userId уникальное значение, присвоенное пользователю при создании;
 * @param isActive желаемое значение статуса активности пользователя.
 */
public record UserSetIsActiveRequest(
        String userId,
        Boolean isActive
) {
}
