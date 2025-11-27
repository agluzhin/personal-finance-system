package agluzhin.personal_finance_system.core.dto.user;

public record UserCreateRequest(
        String login,
        String password
) {
}
