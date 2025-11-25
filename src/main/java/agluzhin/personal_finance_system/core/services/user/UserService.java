package agluzhin.personal_finance_system.core.services.user;

import agluzhin.personal_finance_system.core.dto.user.UserToCreate;
import agluzhin.personal_finance_system.core.dto.user.UserToSetActive;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public ResponseEntity<?> getById(String userId) {
        // TODO: реализовать получение пользователя из InMemoryDataStorage по userId.
        return null;
    }

    public ResponseEntity<?> getItems() {
        // TODO: реализовать получение списка всех пользователей.
        return null;
    }

    public ResponseEntity<?> create(UserToCreate userToCreate) {
        // TODO: реализовать создание пользователя.
        return null;
    }

    public ResponseEntity<?> setIsActive(UserToSetActive userToSetActive) {
        // TODO: реализовать управление статусом пользователя.
        return null;
    }
}
