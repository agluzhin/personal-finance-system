package agluzhin.personal_finance_system.core.models.user;

public record User(
        String userId,
        String login,
        String password,
        boolean isActive
) {
}
