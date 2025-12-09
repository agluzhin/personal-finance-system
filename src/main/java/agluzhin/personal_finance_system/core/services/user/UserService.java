package agluzhin.personal_finance_system.core.services.user;

import agluzhin.personal_finance_system.core.entities.user.User;
import agluzhin.personal_finance_system.core.entities.wallet.Wallet;
import agluzhin.personal_finance_system.core.repositories.UserRepository;
import agluzhin.personal_finance_system.core.repositories.WalletRepository;
import agluzhin.personal_finance_system.core.utils.ValidationUtil;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Данный класс представляет собой сервис, отвечающий за бизнес-логику сущности "User".
 */
@Service
public class UserService {
    /**
     * Метод получения "пользователя" по параметру "userId".
     *
     * @param userId входное значение уникального идентификатора "пользователя".
     * @return экземпляр класса "User".
     */
    public User getById(String userId) {
        ValidationUtil.requireNonNullOrEmpty("id", userId);

        String id = userId.trim();

        ValidationUtil.validateUUID("id", id);
        ValidationUtil.requireExistUserById(id);

        return UserRepository.getUserById(id);
    }

    /**
     * Метод получения всех "пользователей", имеющихся в "UserRepository".
     *
     * @return коллекция экземпляров класса "User".
     */
    public Map<String, User> getItems() {
        return UserRepository.getAllUsers();
    }

    /**
     * Метод создания "пользователя".
     *
     * @param userLogin    входное значение логина "пользователя";
     * @param userPassword входное значение пароля "пользователя";
     * @return экземпляр класса "User".
     */
    public User create(String userLogin, String userPassword) {
        ValidationUtil.requireNonNullOrEmpty("login", userLogin);
        ValidationUtil.requireNonNullOrEmpty("password", userPassword);

        String login = userLogin.trim();
        String password = userPassword.trim();

        ValidationUtil.requireNonExistUserByLogin(login);

        User user = new User(login, password);
        Wallet wallet = new Wallet(user.getWalletId());
        UserRepository.addUserById(user.getId(), user);
        WalletRepository.addWalletById(wallet.getId(), wallet);
        return user;
    }

    /**
     * Метод установки значений "пользователю" по полю "статус активности" (isActive).
     *
     * @param userId       входное значение уникального идентификатора "пользователя";
     * @param userIsActive входное значение статуса активности "пользователя";
     * @return экземпляр класса "User".
     */
    public User setIsActive(String userId, String userIsActive) {
        ValidationUtil.requireNonNullOrEmpty("id", userId);
        ValidationUtil.requireNonNullOrEmpty("isActive", userIsActive);

        String id = userId.trim();
        boolean isActive = userIsActive.equals("true");

        ValidationUtil.validateUUID("id", id);
        ValidationUtil.requireExistUserById(id);

        User user = UserRepository.getUserById(id);
        user.setIsActive(isActive);
        UserRepository.updateUserById(user.getId(), user);
        return user;
    }

    /**
     * Метод авторизации "пользователя".
     *
     * @param userLogin входное значение логина "пользователя";
     * @param userPassword входное значение пароля "пользователя";
     * @return экземпляр класса "User".
     */
    public User authorize(String userLogin, String userPassword) {
        ValidationUtil.requireNonNullOrEmpty("login", userLogin);
        ValidationUtil.requireNonNullOrEmpty("password", userPassword);

        String login = userLogin.trim();
        String password = userPassword.trim();

        ValidationUtil.requireExistUserByLogin(login);

        User user = UserRepository.getUserByLogin(login);

        ValidationUtil.requireUserIsActiveById(user.getId());
        ValidationUtil.validatePassword(login, password);

        user.setIsAuthorize(true);
        UserRepository.updateUserById(user.getId(), user);
        return user;
    }
}
