package agluzhin.personal_finance_system.core.controllers.wallet;

import agluzhin.personal_finance_system.core.dto.wallet.WalletAddCategoryRequest;
import agluzhin.personal_finance_system.core.dto.wallet.WalletDeleteCategoryRequest;
import agluzhin.personal_finance_system.core.services.wallet.WalletService;
import agluzhin.personal_finance_system.core.utils.ResponseUtil;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(
            @RequestParam(value = "userId", required = false) String userId
    ) throws IllegalArgumentException, NoSuchElementException {
        return ResponseUtil.generateSuccessResponse(
                HttpStatus.OK,
                String.format("wallet for user with id - '%s' is exist", userId),
                "wallet",
                walletService.getById(userId)
        );
    }

    @GetMapping("/getItems")
    public ResponseEntity<?> getItems() {
        return ResponseUtil.generateSuccessResponse(
                HttpStatus.OK,
                "all wallets data",
                "data",
                walletService.getItems()
        );
    }

    @PostMapping("/addCategory")
    public ResponseEntity<?> addIncome(
            @RequestBody WalletAddCategoryRequest walletAddCategoryRequest
    ) throws IllegalArgumentException, NoSuchElementException, AuthException {
        return ResponseUtil.generateSuccessResponse(
                HttpStatus.OK,
                "wallet successfully updated",
                "wallet",
                walletService.addCategory(walletAddCategoryRequest)
        );
    }

    @DeleteMapping("/deleteCategory")
    public ResponseEntity<?> deleteIncome(
            @RequestBody WalletDeleteCategoryRequest WalletDeleteCategoryRequest
    ) throws IllegalArgumentException, NoSuchElementException, AuthException {
        return ResponseUtil.generateSuccessResponse(
                HttpStatus.OK,
                "wallet successfully updated",
                "wallet",
                walletService.deleteCategory(WalletDeleteCategoryRequest)
        );
    }
}
