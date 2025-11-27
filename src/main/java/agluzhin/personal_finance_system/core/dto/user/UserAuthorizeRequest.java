package agluzhin.personal_finance_system.core.dto.user;

public record UserAuthorizeRequest(
        String login,
        String password
) {
}
