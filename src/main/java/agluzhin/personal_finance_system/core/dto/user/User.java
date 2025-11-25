package agluzhin.personal_finance_system.core.dto.user;

public record User(
        String userId,
        String login,
        String password,
        boolean isActive,
        boolean isAuthorized,
        String walletId
) {
}
