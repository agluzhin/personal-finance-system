package agluzhin.personal_finance_system.core.dto.user;


/**
 * DTO для запроса на авторизацию пользователя по логину и паролю.
 * @param login логин, указанный при создании пользователя;
 * @param password пароль, указанный при создании пользователя.
 */
public record UserAuthorizeRequest(
        String login,
        String password
) {
}
