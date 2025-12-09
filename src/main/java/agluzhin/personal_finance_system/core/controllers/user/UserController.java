package agluzhin.personal_finance_system.core.controllers.user;

import agluzhin.personal_finance_system.core.services.user.UserService;
import agluzhin.personal_finance_system.core.utils.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Данный класс является контроллером входных HTTP-запросов (от клиента) для endpoint'ов коллекции "/api/users".
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    // Сервис, отвечающий за бизнес-логику сущности "User".
    private final UserService userService;

    /**
     * Конструктор присвоения значения по полю "userService".
     *
     * @param userService сервис, отвечающий за бизнес-логику сущности "User".
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Обработчик метода получения "пользователя" по параметру "id".
     *
     * @param id входное значение уникального идентификатора "пользователя".
     * @return экземпляр класса "ResponseEntity" с соответствующими данными.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseUtil.generateSuccessResponse(
                HttpStatus.OK,
                String.format("user with id '%s' found", id),
                "user",
                userService.getById(id)
        );
    }

    /**
     * Обработчик метода получения всех "пользователей", имеющихся в "UserRepository".
     *
     * @return экземпляр класса "ResponseEntity" с соответствующими данными.
     */
    @GetMapping
    public ResponseEntity<?> getItems() {
        return ResponseUtil.generateSuccessResponse(
                HttpStatus.OK,
                "all users data",
                "data",
                userService.getItems()
        );
    }

    /**
     * Обработчик метода создания "пользователя".
     *
     * @param login    входное значение логина "пользователя";
     * @param password входное значение пароля "пользователя".
     * @return экземпляр класса "ResponseEntity" с соответствующими данными.
     */
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam String login, @RequestParam String password) {
        return ResponseUtil.generateSuccessResponse(
                HttpStatus.CREATED,
                "user successfully created",
                "user",
                userService.create(login, password)
        );
    }

    /**
     * Обработчик метода установки значений "пользователю" по полю "статус активности" (isActive).
     *
     * @param id       входное значение уникального идентификатора "пользователя";
     * @param isActive входное значение статуса активности "пользователя" ("true"/"false").
     * @return экземпляр класса "ResponseEntity" с соответствующими данными.
     */
    @PatchMapping("{id}/set")
    public ResponseEntity<?> setIsActive(@PathVariable String id, @RequestParam String isActive) {
        return ResponseUtil.generateSuccessResponse(
                HttpStatus.OK,
                "user successfully updated",
                "user",
                userService.setIsActive(id, isActive)
        );

    }

    /**
     * Обработчик метода авторизации "пользователя"
     *
     * @param id входное значение уникального идентификатора "пользователя";
     * @param login входное значение логина "пользователя";
     * @param password входное значение пароля "пользователя".
     * @return экземпляр класса "ResponseEntity" с соответствующими данными.
     */
    @PatchMapping("{id}/authorize")
    public ResponseEntity<?> authorize(
            @PathVariable String id,
            @RequestParam String login,
            @RequestParam String password
    ) {
        return ResponseUtil.generateSuccessResponse(
                HttpStatus.OK,
                "user successfully authorized",
                "user",
                userService.authorize(login, password)
        );
    }
}
