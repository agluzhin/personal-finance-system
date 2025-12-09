package agluzhin.personal_finance_system.core.controllers.wallet;

import agluzhin.personal_finance_system.core.services.wallet.WalletService;
import agluzhin.personal_finance_system.core.utils.ResponseUtil;

import jakarta.security.auth.message.AuthException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Данный класс является контроллером входных HTTP-запросов (от клиента) для endpoint'ов коллекции "/api/wallets".
 */
@RestController
@RequestMapping("/api/wallets")
public class WalletController {
    // Сервис, отвечающий за бизнес-логику сущности "Wallet".
    private final WalletService walletService;

    /**
     * Конструктор присвоения значения по полю "walletService".
     *
     * @param walletService сервис, отвечающий за бизнес-логику сущности "Wallet".
     */
    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    /**
     * Обработчик метода получения "кошелька" по параметру "id".
     *
     * @param id входное значение уникального идентификатора "кошелька".
     * @return экземпляр класса "ResponseEntity" с соответствующими данными.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseUtil.generateSuccessResponse(
                HttpStatus.OK,
                String.format("wallet with id '%s' found", id),
                "wallet",
                walletService.getById(id)
        );
    }

    /**
     * Обработчик метода получения информации о "кошельке" по параметру "id".
     *
     * @param id входное значение уникального идентификатора "кошелька".
     * @return экземпляр класса "ResponseEntity" с соответствующими данными.
     */
    @GetMapping("/{id}/info")
    public ResponseEntity<?> getInfo(@PathVariable String id) {
        return ResponseUtil.generateSuccessResponse(
                HttpStatus.OK,
                "wallet info",
                "info",
                walletService.getInfoById(id)
        );
    }

    /**
     * Обработчик метода получения всех "кошельков", имеющихся в "WalletRepository".
     *
     * @return экземпляр класса "ResponseEntity" с соответствующими данными.
     */
    @GetMapping
    public ResponseEntity<?> getItems() {
        return ResponseUtil.generateSuccessResponse(
                HttpStatus.OK,
                "all wallets data",
                "data",
                walletService.getItems()
        );
    }


    /**
     * Обработчик метода добавления "категории" в "кошелек".
     *
     * @param id    входное значение уникального идентификатора "кошелька";
     * @param type  входное значение категории ("income"/"expenditure");
     * @param name  входное значение названия категории;
     * @param value входное значение суммы по данной категории.
     * @return экземпляр класса "ResponseEntity" с соответствующими данными.
     * @throws AuthException исключение, связанное с отсутствием авторизации у пользователя.
     */
    @PostMapping("/{id}/categories/add")
    public ResponseEntity<?> addCategoryToWallet(
            @PathVariable String id,
            @RequestParam String type,
            @RequestParam String name,
            @RequestParam String value
    ) throws AuthException {
        return ResponseUtil.generateSuccessResponse(
                HttpStatus.OK,
                "category successfully added",
                "wallet",
                walletService.addCategory(id, type, name, value)
        );
    }

    /**
     * Обработчик метода удаления "категории" из "кошелька".
     *
     * @param id   входное значение уникального идентификатора "кошелька";
     * @param type входное значение категории ("income"/"expenditure");
     * @param name входное значение названия категории.
     * @return экземпляр класса "ResponseEntity" с соответствующими данными.
     * @throws AuthException исключение, связанное с отсутствием авторизации у пользователя.
     */
    @DeleteMapping("/{id}/categories/delete")
    public ResponseEntity<?> deleteCategoryFromWallet(
            @PathVariable String id,
            @RequestParam String type,
            @RequestParam String name
    ) throws AuthException {
        return ResponseUtil.generateSuccessResponse(
                HttpStatus.OK,
                "category successfully deleted",
                "wallet",
                walletService.deleteCategory(id, type, name)
        );
    }

    /**
     * Обработчик метода добавления "бюджета" в "кошелек".
     *
     * @param id    входное значение уникального идентификатора "кошелька";
     * @param name  входное значение названия бюджета;
     * @param value входное значение размера данного бюджета.
     * @return экземпляр класса "ResponseEntity" с соответствующими данными.
     * @throws AuthException исключение, связанное с отсутствием авторизации у пользователя.
     */
    @PostMapping("/{id}/budgets/add")
    public ResponseEntity<?> addBudgetToWallet(
            @PathVariable String id,
            @RequestParam String name,
            @RequestParam String value
    ) throws AuthException {
        return ResponseUtil.generateSuccessResponse(
                HttpStatus.OK,
                "budget successfully added",
                "wallet",
                walletService.addBudget(id, name, value)
        );
    }

    /**
     * Обработчик метода удаления "бюджета" из "кошелька".
     *
     * @param id   входное значение уникального идентификатора "кошелька";
     * @param name входное значение названия бюджета.
     * @return экземпляр класса "ResponseEntity" с соответствующими данными.
     * @throws AuthException исключение, связанное с отсутствием авторизации у пользователя.
     */
    @DeleteMapping("/{id}/budgets/delete")
    public ResponseEntity<?> deleteBudgetFromWallet(
            @PathVariable String id,
            @RequestParam String name
    ) throws AuthException {
        return ResponseUtil.generateSuccessResponse(
                HttpStatus.OK,
                "budget successfully deleted",
                "wallet",
                walletService.deleteBudget(id, name)
        );
    }
}
