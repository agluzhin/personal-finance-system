package agluzhin.personal_finance_system.core.entities.wallet;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Wallet {
    String walletId;
    // тип категории => { название категории => значение }
    Map<String, Map<String, BigDecimal>> categories;
    // название категории => значение
    Map<String, BigDecimal> budget;

    public Wallet() {
        walletId = UUID.randomUUID().toString();
        categories = new HashMap<>();
        budget = new HashMap<>();
    }

    public String getWalletId() {
        return walletId;
    }

    public Map<String, Map<String, BigDecimal>> getCategories() {
        return categories;
    }

    public Map<String, BigDecimal> getBudget() {
        return budget;
    }
}
