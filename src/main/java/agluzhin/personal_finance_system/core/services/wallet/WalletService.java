package agluzhin.personal_finance_system.core.services.wallet;

import agluzhin.personal_finance_system.core.dtos.WalletInfo;
import agluzhin.personal_finance_system.core.entities.wallet.Wallet;
import agluzhin.personal_finance_system.core.repositories.WalletRepository;
import agluzhin.personal_finance_system.core.utils.ValidationUtil;

import jakarta.security.auth.message.AuthException;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Данный класс представляет собой сервис, отвечающий за бизнес-логику сущности "Wallet".
 */
@Service
public class WalletService {

    /**
     * Метод получения "кошелька" по параметру "walletId".
     * @param walletId входное значение уникального идентификатора "кошелька".
     * @return экземпляр класса "Wallet".
     */
    public Wallet getById(String walletId) {
        ValidationUtil.requireNonNullOrEmpty("id", walletId);

        String id = walletId.trim();

        ValidationUtil.validateUUID("id", id);
        ValidationUtil.requireExistWalletById(id);

        return WalletRepository.getWalletById(id);
    }

    /**
     * Метод получения информации о "кошельке" по параметру "walletId".
     * @param walletId входное значение уникального идентификатора "кошелька".
     * @return экземпляр record'а "WalletInfo" (DTO).
     */
    public WalletInfo getInfoById(String walletId) {
        ValidationUtil.requireNonNullOrEmpty("id", walletId);

        String id = walletId.trim();

        ValidationUtil.validateUUID("id", id);
        ValidationUtil.requireExistWalletById(id);

        return WalletRepository.getWalletInfoById(id);
    }

    /**
     * Метод получения всех "кошельков", имеющихся в "WalletRepository".
     * @return коллекция экземпляров класса "Wallet".
     */
    public Map<String, Wallet> getItems() {
        return WalletRepository.getAllWallets();
    }

    /**
     * Метод добавления "категории" в "кошелек".
     * @param walletId входное значение уникального идентификатора "кошелька";
     * @param categoryType входное значение категории ("income"/"expenditure");
     * @param categoryName входное значение названия категории;
     * @param categoryValue входное значение суммы по данной категории.
     * @return экземпляр класса "Wallet".
     * @throws AuthException исключение, связанное с отсутствием авторизации у пользователя.
     */
    public Wallet addCategory(String walletId, String categoryType, String categoryName, String categoryValue)
            throws AuthException {
        ValidationUtil.requireNonNullOrEmpty("id", walletId);
        ValidationUtil.requireNonNullOrEmpty("type", categoryType);
        ValidationUtil.requireNonNullOrEmpty("name", categoryName);
        ValidationUtil.requireNonNullOrEmpty("value", categoryValue);

        String id = walletId.trim();
        String type = categoryType.trim();
        String name = categoryName.trim();
        String value = categoryValue.trim();

        ValidationUtil.validateUUID("id", id);
        ValidationUtil.requireExistWalletById(id);
        ValidationUtil.requireUserIsActiveByWalletId(id);
        ValidationUtil.requireUserIsAuthorizeByWalletId(id);
        ValidationUtil.validateCategoryType(type);

        Wallet wallet = WalletRepository.getWalletById(id);
        Map<String, Map<String, BigDecimal>> categories = wallet.getCategories();
        if (categories.containsKey(type)) {
            Map<String, BigDecimal> items = categories.get(type);
            items.merge(name, new BigDecimal(value), BigDecimal::add);
        } else {
            Map<String, BigDecimal> newItem = new HashMap<>();
            newItem.put(name, new BigDecimal(value));
            categories.put(type, newItem);
        }
        WalletRepository.updateWalletById(id, wallet);
        return wallet;
    }

    /**
     * Метод удаления "категории" из "кошелька".
     * @param walletId входное значение уникального идентификатора "кошелька";
     * @param categoryType входное значение категории ("income"/"expenditure");
     * @param categoryName входное значение названия категории.
     * @return экземпляр класса "Wallet".
     * @throws AuthException исключение, связанное с отсутствием авторизации у пользователя.
     */
    public Wallet deleteCategory(String walletId, String categoryType, String categoryName)
            throws AuthException {
        ValidationUtil.requireNonNullOrEmpty("id", walletId);
        ValidationUtil.requireNonNullOrEmpty("type", categoryType);
        ValidationUtil.requireNonNullOrEmpty("name", categoryName);

        String id = walletId.trim();
        String type = categoryType.trim();
        String name = categoryName.trim();

        ValidationUtil.validateUUID("id", id);
        ValidationUtil.requireExistWalletById(id);
        ValidationUtil.requireUserIsActiveByWalletId(id);
        ValidationUtil.requireUserIsAuthorizeByWalletId(id);
        ValidationUtil.validateCategoryType(type);

        Wallet wallet = WalletRepository.getWalletById(id);

        ValidationUtil.requireExistCategoryByName(wallet, type, name);

        wallet.getCategories().get(type).remove(name);
        WalletRepository.updateWalletById(id, wallet);
        return wallet;
    }

    /**
     * Метод добавления "бюджета" в "кошелек".
     * @param walletId входное значение уникального идентификатора "кошелька";
     * @param budgetName входное значение названия бюджета;
     * @param budgetValue входное значение размера данного бюджета.
     * @return экземпляр класса "Wallet".
     * @throws AuthException исключение, связанное с отсутствием авторизации у пользователя.
     */
    public Wallet addBudget(String walletId, String budgetName, String budgetValue) throws AuthException {
        ValidationUtil.requireNonNullOrEmpty("id", walletId);
        ValidationUtil.requireNonNullOrEmpty("name", budgetName);
        ValidationUtil.requireNonNullOrEmpty("value", budgetValue);

        String id = walletId.trim();
        String name = budgetName.trim();
        String value = budgetValue.trim();

        ValidationUtil.validateUUID("id", id);
        ValidationUtil.requireExistWalletById(id);
        ValidationUtil.requireUserIsActiveByWalletId(id);
        ValidationUtil.requireUserIsAuthorizeByWalletId(id);

        Wallet wallet = WalletRepository.getWalletById(id);

        ValidationUtil.requireExistCategoryByName(wallet, "expenditure", name);

        Map<String, BigDecimal> budgets = wallet.getBudgets();
        if (budgets.containsKey(name)) {
            budgets.merge(name, new BigDecimal(value), BigDecimal::add);
        } else {
            budgets.put(name, new BigDecimal(value));
        }
        WalletRepository.updateWalletById(id, wallet);
        return wallet;
    }

    /**
     * Метод удаления "бюджета" из "кошелька".
     * @param walletId входное значение уникального идентификатора "кошелька";
     * @param budgetName входное значение названия бюджета.
     * @return экземпляр класса "Wallet".
     * @throws AuthException исключение, связанное с отсутствием авторизации у пользователя.
     */
    public Wallet deleteBudget(String walletId, String budgetName) throws AuthException {
        ValidationUtil.requireNonNullOrEmpty("id", walletId);
        ValidationUtil.requireNonNullOrEmpty("name", budgetName);

        String id = walletId.trim();
        String name = budgetName.trim();

        ValidationUtil.validateUUID("id", id);
        ValidationUtil.requireExistWalletById(id);
        ValidationUtil.requireUserIsActiveByWalletId(id);
        ValidationUtil.requireUserIsAuthorizeByWalletId(id);

        Wallet wallet = WalletRepository.getWalletById(id);

        ValidationUtil.requireExistBudgetByName(wallet, name);

        wallet.getBudgets().remove(name);
        WalletRepository.updateWalletById(id, wallet);
        return wallet;
    }
}
