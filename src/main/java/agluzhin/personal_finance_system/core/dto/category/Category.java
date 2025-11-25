package agluzhin.personal_finance_system.core.dto.category;

import java.math.BigDecimal;

public record Category(
        String categoryName,
        BigDecimal categoryValue
) {
}
