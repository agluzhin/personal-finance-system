package agluzhin.personal_finance_system.core.dto.wallet;

import java.math.BigDecimal;
import java.util.Map;

public record Wallet(
        String walletId,
        // тип категории => { название категории => значение }
        Map<String, Map<String, BigDecimal>> categories,
        // название категории => значение
        Map<String, BigDecimal> budget
) {
}
