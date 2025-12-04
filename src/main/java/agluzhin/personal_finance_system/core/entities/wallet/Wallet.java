package agluzhin.personal_finance_system.core.entities.wallet;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Данный класс представляет собой сущность "Wallet" (кошелек).
 * <p>Используется для хранения:</p>
 * - уникального идентификатора (walletId);<br>
 * - категорий по типу ДОХОДЫ/РАСХОДЫ (categories);<br>
 * - бюджетов по категориям (budgets).
 */
public class Wallet {
    String walletId;
    Map<String, Map<String, BigDecimal>> categories;
    Map<String, BigDecimal> budgets;

    public Wallet() {
        walletId = UUID.randomUUID().toString();
        categories = new HashMap<>();
        budgets = new HashMap<>();
    }


    /**
     * Базовый getter для уникального идентификатора кошелька (walletId).
     * @return значение walletId.
     */
    public String getWalletId() {
        return walletId;
    }

    /**
     * Базовый getter для категорий по типу ДОХОДЫ/РАСХОДЫ (categories).
     * @return коллекция значений categories.
     */
    public Map<String, Map<String, BigDecimal>> getCategories() {
        return categories;
    }

    /**
     * Базовый getter для бюджетов по категориям (budgets)
     * @return коллекция значений budgets.
     */
    public Map<String, BigDecimal> getBudgets() {
        return budgets;
    }
}
