package agluzhin.personal_finance_system.core.services.wallet;

import agluzhin.personal_finance_system.core.dto.wallet.WalletAddCategoryRequest;
import agluzhin.personal_finance_system.core.dto.wallet.WalletDeleteCategoryRequest;
import agluzhin.personal_finance_system.core.entities.wallet.Wallet;
import agluzhin.personal_finance_system.core.repositories.InMemoryDataStorage;
import agluzhin.personal_finance_system.core.utils.ValidationUtil;
import jakarta.security.auth.message.AuthException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class WalletService {

    public Wallet getById(String userId)
            throws IllegalArgumentException, NoSuchElementException {
        ValidationUtil.requireNonEmpty("userId", userId);
        ValidationUtil.validateUUID("userId", userId);
        ValidationUtil.requireExistUserById(InMemoryDataStorage.users, userId);

        return InMemoryDataStorage.users.get(userId).getWallet();
    }

    public Map<String, Wallet> getItems() {
        return InMemoryDataStorage.wallets;
    }

    public Wallet addCategory(WalletAddCategoryRequest walletAddCategoryRequest)
            throws IllegalArgumentException, NoSuchElementException, AuthException {
        String userId = walletAddCategoryRequest.userId();
        String categoryType = walletAddCategoryRequest.categoryType();
        String categoryName = walletAddCategoryRequest.categoryName();
        String categoryValue = walletAddCategoryRequest.categoryValue();

        ValidationUtil.requireNonEmpty("userId", userId);
        ValidationUtil.validateUUID("userId", userId);
        ValidationUtil.requireExistUserById(InMemoryDataStorage.users, userId);
        ValidationUtil.requireUserAuthorized(InMemoryDataStorage.users, userId);
        ValidationUtil.requireNonEmpty("categoryType", categoryType);
        ValidationUtil.validateCategoryType(categoryType);
        ValidationUtil.requireNonEmpty("categoryName", categoryName);
        ValidationUtil.requireNonEmpty("categoryValue", categoryValue);

        Wallet wallet = InMemoryDataStorage.users.get(userId).getWallet();
        Map<String, Map<String, BigDecimal>> categories = wallet.getCategories();
        if (categories.containsKey(categoryType)) {
            Map<String, BigDecimal> items = categories.get(categoryType);
            items.merge(categoryName.trim(), new BigDecimal(categoryValue), BigDecimal::add);
        } else {
            Map<String, BigDecimal> newItem = new HashMap<>();
            newItem.put(categoryName.trim(), new BigDecimal(categoryValue));
            categories.put(categoryType, newItem);
        }
        return wallet;
    }

    public Wallet deleteCategory(WalletDeleteCategoryRequest walletDeleteCategoryRequest)
            throws IllegalArgumentException, NoSuchElementException, AuthException {
        String userId = walletDeleteCategoryRequest.userId();
        String categoryType = walletDeleteCategoryRequest.categoryType();
        String categoryName = walletDeleteCategoryRequest.categoryName();

        ValidationUtil.requireNonEmpty("userId", userId);
        ValidationUtil.validateUUID("userId", userId);
        ValidationUtil.requireExistUserById(InMemoryDataStorage.users, userId);
        ValidationUtil.requireUserAuthorized(InMemoryDataStorage.users, userId);
        ValidationUtil.requireNonEmpty("categoryType", categoryType);
        ValidationUtil.validateCategoryType(categoryType);
        ValidationUtil.requireNonEmpty("categoryName", categoryName);
        ValidationUtil.requireExistCategoryByName(InMemoryDataStorage.users.get(userId).getWallet(), categoryType, categoryName);

        Wallet wallet = InMemoryDataStorage.users.get(userId).getWallet();
        wallet.getCategories().get(categoryType).remove(categoryName);
        return wallet;
    }
}
