package agluzhin.personal_finance_system.core.entities.user;

import agluzhin.personal_finance_system.core.entities.wallet.Wallet;
import agluzhin.personal_finance_system.core.utils.PasswordUtil;

import java.util.UUID;

public class User {
    String userId;
    String login;
    String password;
    boolean isActive;
    boolean isAuthorized;
    Wallet wallet;

    public User(String login, String password) {
        userId = UUID.randomUUID().toString();
        this.login = login;
        this.password = PasswordUtil.encode(password);
        isActive = true;
        isAuthorized = false;
        wallet = new Wallet();
    }

    public String getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsAuthorized() {
        return isAuthorized;
    }

    public void setIsAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }
}
