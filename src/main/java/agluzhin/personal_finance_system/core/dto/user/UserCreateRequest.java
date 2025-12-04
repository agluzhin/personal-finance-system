package agluzhin.personal_finance_system.core.dto.user;

/**
 * DTO для запроса на создание пользователя по логину и паролю.
 * @param login желаемое значение логина;
 * @param password желаемое значение пароля.
 */
public record UserCreateRequest(
        String login,
        String password
) {
}
