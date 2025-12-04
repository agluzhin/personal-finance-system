package agluzhin.personal_finance_system.core.services.user;

import agluzhin.personal_finance_system.core.dto.user.UserAuthorizeRequest;
import agluzhin.personal_finance_system.core.dto.user.UserCreateRequest;
import agluzhin.personal_finance_system.core.dto.user.UserSetIsActiveRequest;
import agluzhin.personal_finance_system.core.entities.user.User;
import agluzhin.personal_finance_system.core.repositories.InMemoryDataStorage;

import agluzhin.personal_finance_system.core.utils.ValidationUtil;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    public User getById(String userId)
            throws IllegalArgumentException, NoSuchElementException {
        ValidationUtil.requireNonEmpty("userId", userId);
        ValidationUtil.validateUUID("userId", userId);
        ValidationUtil.requireExistUserById(InMemoryDataStorage.users, userId);

        return InMemoryDataStorage.users.get(userId);
    }

    public Map<String, User> getItems() {
        return InMemoryDataStorage.users;
    }

    public User create(UserCreateRequest userCreateRequest)
            throws IllegalArgumentException {
        ValidationUtil.requireNonNull("login", userCreateRequest.login());
        ValidationUtil.requireNonNull("password", userCreateRequest.password());
        ValidationUtil.requireNonExistUserByLogin(InMemoryDataStorage.users, userCreateRequest.login());

        User user = new User(userCreateRequest.login().trim(), userCreateRequest.password().trim());
        InMemoryDataStorage.users.put(user.getUserId(), user);
        InMemoryDataStorage.wallets.put(user.getWallet().getWalletId(), user.getWallet());
        return user;
    }

    public User setIsActive(UserSetIsActiveRequest userSetIsActiveRequest)
            throws IllegalArgumentException, NoSuchElementException {
        ValidationUtil.requireNonEmpty("userId", userSetIsActiveRequest.userId());
        ValidationUtil.validateUUID("userId", userSetIsActiveRequest.userId());
        ValidationUtil.requireNonNull("isActive", userSetIsActiveRequest.isActive());
        ValidationUtil.requireExistUserById(InMemoryDataStorage.users, userSetIsActiveRequest.userId());

        User user = InMemoryDataStorage.users.get(userSetIsActiveRequest.userId());
        user.setIsActive(userSetIsActiveRequest.isActive());
        return user;
    }

    public User authorize(UserAuthorizeRequest userAuthorizeRequest)
            throws IllegalArgumentException, NoSuchElementException {
        String login = userAuthorizeRequest.login();
        String password = userAuthorizeRequest.password();

        ValidationUtil.requireNonEmpty("login", login);
        ValidationUtil.requireNonEmpty("password", password);
        ValidationUtil.requireExistUserByLogin(InMemoryDataStorage.users, login);
        String userId = ValidationUtil.validatePassword(InMemoryDataStorage.users, login, password);

        User user = InMemoryDataStorage.users.get(userId);
        user.setIsAuthorized(true);
        return user;
    }
}
