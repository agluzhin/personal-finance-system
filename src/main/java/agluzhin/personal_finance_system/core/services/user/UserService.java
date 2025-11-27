package agluzhin.personal_finance_system.core.services.user;

import agluzhin.personal_finance_system.core.dto.user.UserAuthorizeRequest;
import agluzhin.personal_finance_system.core.dto.user.UserCreateRequest;
import agluzhin.personal_finance_system.core.dto.user.UserSetIsActiveRequest;
import agluzhin.personal_finance_system.core.entities.user.User;
import agluzhin.personal_finance_system.core.repositories.InMemoryDataStorage;
import agluzhin.personal_finance_system.core.utils.PasswordUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class UserService {
    private static final Pattern UUID_PATTERN = Pattern.compile(
            "^[0-9a-f]{8}-[0-9a-f]{4}-[0-5][0-9a-f]{3}-[089ab][0-9a-f]{3}-[0-9a-f]{12}$"
    );

    public User getById(String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("field 'userId' can not be null or empty");
        }
        if (!UUID_PATTERN.matcher(userId).matches()) {
            throw new IllegalArgumentException("invalid UUID format for 'userId'");
        }
        if (!isUserExistById(userId)) {
            throw new NoSuchElementException(String.format("user with id '%s' not found", userId));
        }
        return InMemoryDataStorage.users.get(userId);
    }

    public Map<String, User> getItems() {
        return InMemoryDataStorage.users;
    }

    public User create(UserCreateRequest userCreateRequest) {
        if (userCreateRequest.login() == null || userCreateRequest.login().isEmpty()) {
            throw new IllegalArgumentException("field 'login' can not be null or empty");
        }
        if (userCreateRequest.password() == null || userCreateRequest.password().isEmpty()) {
            throw new IllegalArgumentException("field 'password' can not be null or empty");
        }
        if (isUserExistByLogin(userCreateRequest.login())) {
            throw new IllegalArgumentException(String.format("user with login '%s' is already exist", userCreateRequest.login()));
        }
        User user = new User(userCreateRequest.login(), userCreateRequest.password());
        InMemoryDataStorage.users.put(user.getUserId(), user);
        InMemoryDataStorage.wallets.put(user.getWallet().getWalletId(), user.getWallet());
        return user;
    }

    public User setIsActive(UserSetIsActiveRequest userSetIsActiveRequest) {
        if (userSetIsActiveRequest.userId() == null || userSetIsActiveRequest.userId().isEmpty()) {
            throw new IllegalArgumentException("field 'userId' can not be null or empty");
        }
        if (userSetIsActiveRequest.isActive() == null) {
            throw new IllegalArgumentException("field 'isActive' can not be null or empty");
        }
        if (!UUID_PATTERN.matcher(userSetIsActiveRequest.userId()).matches()) {
            throw new IllegalArgumentException("invalid UUID format for 'userId'");
        }
        if (!InMemoryDataStorage.users.containsKey(userSetIsActiveRequest.userId())) {
            throw new NoSuchElementException(String.format("user with id '%s' not found", userSetIsActiveRequest.userId()));
        }
        User user = InMemoryDataStorage.users.get(userSetIsActiveRequest.userId());
        user.setIsActive(userSetIsActiveRequest.isActive());
        return user;
    }

    public User authorize(UserAuthorizeRequest userAuthorizeRequest) {
        String login = userAuthorizeRequest.login();
        String password = userAuthorizeRequest.password();
        if (login == null || login.isEmpty()) {
            throw new IllegalArgumentException("field 'login' can not be null or empty");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("field 'password' can not be null or empty");
        }
        if (!isUserExistByLogin(login)) {
            throw new NoSuchElementException(String.format("user with login '%s' not found", login));
        }
        String userId = "";
        for (User user : InMemoryDataStorage.users.values()) {
            if (user.getLogin().equals(login)) {
                if (PasswordUtil.matches(password, user.getPassword())) {
                    userId = user.getUserId();
                    break;
                } else {
                    throw new IllegalArgumentException("incorrect 'password' given");
                }
            }
        }
        User user = InMemoryDataStorage.users.get(userId);
        user.setIsAuthorized(true);
        return user;
    }

    private boolean isUserExistById(String userId) {
        return InMemoryDataStorage.users.containsKey(userId);
    }

    private boolean isUserExistByLogin(String login) {
        for (User user : InMemoryDataStorage.users.values()) {
            if (user.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }
}
