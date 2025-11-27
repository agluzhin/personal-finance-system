package agluzhin.personal_finance_system.core.dto.user;

public record UserSetIsActiveRequest(
        String userId,
        Boolean isActive
) {
}
