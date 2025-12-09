package agluzhin.personal_finance_system.core.repositories;

import agluzhin.personal_finance_system.core.dtos.WalletInfo;
import agluzhin.personal_finance_system.core.entities.wallet.Wallet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Данный класс содержит всю информацию о коллекции экземпляров класса "Wallet".
 * Используется для манипуляции данной коллекцией.
 */
@Component
public class WalletRepository {
    // Присвоение хранилища "кошельков".
    private static final Map<String, Wallet> WALLET_STORAGE = InMemoryDataStorage.wallets;

    /**
     * Метод получения "кошелька" по параметру "id".
     *
     * @param id входное значение уникального идентификатора "кошелька";
     * @return экземпляр класса "Wallet".
     */
    public static Wallet getWalletById(String id) {
        return WALLET_STORAGE.get(id);
    }

    /**
     * Метод получения всех "кошельков" из коллекции.
     *
     * @return коллекция экземпляров класса "Wallet".
     */
    public static Map<String, Wallet> getAllWallets() {
        return WALLET_STORAGE;
    }

    /**
     * Метод получения категорий "кошелька" по параметру "id".
     *
     * @param id   входное значение уникального идентификатора "кошелька";
     * @param type входное значения типа категории "кошелька" ("income"/"expenditure").
     * @return коллекцию значений категорий данного типа.
     */
    public static Map<String, BigDecimal> getCategoriesById(String id, String type) {
        return WALLET_STORAGE.get(id).getCategories().get(type);
    }

    /**
     * Метод получения бюджетов "кошелька" по параметру "id".
     *
     * @param id входное значение уникального идентификатора "кошелька";
     * @return коллекцию значений бюджетов.
     */
    public static Map<String, BigDecimal> getBudgetsById(String id) {
        Map<String, BigDecimal> result = new HashMap<>();
        Map<String, BigDecimal> budgets = WALLET_STORAGE.get(id).getBudgets();
        Map<String, BigDecimal> expenditures = WALLET_STORAGE.get(id).getCategories().get("expenditure");
        for (String categoryName : expenditures.keySet()) {
            if (budgets.containsKey(categoryName)) {
                String key = String.format("Оставшийся бюджет для категории '%s'", categoryName);
                BigDecimal value = budgets.get(categoryName).subtract(expenditures.get(categoryName));
                result.put(key, value);
            }
        }
        return result;
    }

    /**
     * Метод расчета общего значения по типу категории "кошелька".
     * @param id входное значение уникального идентификатора "кошелька";
     * @param type входное значения типа категории "кошелька" ("income"/"expenditure").
     * @return размер суммы по выбранному типу.
     */
    public static BigDecimal calculateTotalById(String id, String type) {
        BigDecimal result = new BigDecimal("0");
        for (BigDecimal incomeCategoryValue : WALLET_STORAGE.get(id).getCategories().get(type).values()) {
            result = result.add(incomeCategoryValue);
        }
        return result;
    }

    /**
     * Метод получения информации о "кошельке" по параметру "id".
     * @param id входное значение уникального идентификатора "кошелька".
     * @return экземпляр record'а WalletInfo (DTO для отображения информации).
     */
    public static WalletInfo getWalletInfoById(String id) {
        return new WalletInfo(
                calculateTotalById(id, "income"),
                getCategoriesById(id, "income"),
                calculateTotalById(id, "expenditure"),
                getCategoriesById(id, "expenditure"),
                getBudgetsById(id)
        );
    }

    /**
     * Метод добавления "кошелька" по его "id".
     *
     * @param id     уникальный идентификатор "кошелька";
     * @param wallet экземпляр класса "Wallet".
     */
    public static void addWalletById(String id, Wallet wallet) {
        WALLET_STORAGE.put(id, wallet);
    }

    /**
     * Метод обновления "кошелька" по его "id".
     *
     * @param id     уникальный идентификатор "кошелька";
     * @param wallet экземпляр класса "Wallet".
     */
    public static void updateWalletById(String id, Wallet wallet) {
        WALLET_STORAGE.replace(id, wallet);
    }
}
