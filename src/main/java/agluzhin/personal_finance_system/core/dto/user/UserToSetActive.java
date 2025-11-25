package agluzhin.personal_finance_system.core.dto.user;

public record UserToSetActive(
        String userId,
        boolean isActive
) {
}
