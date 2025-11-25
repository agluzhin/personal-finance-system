package agluzhin.personal_finance_system.core.dto.user;

public record UserToCreate(
        String login,
        String password
) {
}
