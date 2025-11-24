package agluzhin.personal_finance_system.core.models.user;

public record UserToCreate(
        String login,
        String password
) {
}
