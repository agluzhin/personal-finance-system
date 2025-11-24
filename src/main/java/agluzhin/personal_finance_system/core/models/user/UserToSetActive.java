package agluzhin.personal_finance_system.core.models.user;

public record UserToSetActive(
        String userId,
        boolean isActive
) {
}
