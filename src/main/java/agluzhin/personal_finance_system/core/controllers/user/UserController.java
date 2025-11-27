package agluzhin.personal_finance_system.core.controllers.user;

import agluzhin.personal_finance_system.core.dto.user.UserAuthorizeRequest;
import agluzhin.personal_finance_system.core.dto.user.UserCreateRequest;
import agluzhin.personal_finance_system.core.dto.user.UserSetIsActiveRequest;
import agluzhin.personal_finance_system.core.services.user.UserService;
import agluzhin.personal_finance_system.core.utils.ResponseUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(
            @RequestParam(value = "userId", required = false) String userId
    ) throws IllegalArgumentException, NoSuchElementException {
        return ResponseUtil.generateSuccessResponse(
                HttpStatus.OK,
                String.format("user with id - '%s' is exist", userId),
                "user",
                userService.getById(userId)
        );
    }

    @GetMapping("/getItems")
    public ResponseEntity<?> getItems() {
        return ResponseUtil.generateSuccessResponse(
                HttpStatus.OK,
                "all users data",
                "data",
                userService.getItems()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(
            @RequestBody UserCreateRequest userCreateRequest
    ) throws IllegalArgumentException {
        return ResponseUtil.generateSuccessResponse(
                HttpStatus.CREATED,
                "user successfully created",
                "user",
                userService.create(userCreateRequest)
        );
    }

    @PostMapping("/setIsActive")
    public ResponseEntity<?> setIsActive(
            @RequestBody UserSetIsActiveRequest userSetIsActiveRequest
    ) throws IllegalArgumentException, NoSuchElementException {
        return ResponseUtil.generateSuccessResponse(
                HttpStatus.OK,
                "user successfully updated",
                "user",
                userService.setIsActive(userSetIsActiveRequest)
        );

    }

    @PostMapping("/authorize")
    public ResponseEntity<?> authorize(
            @RequestBody UserAuthorizeRequest userAuthorizeRequest
    ) throws IllegalArgumentException, NoSuchElementException {
        return ResponseUtil.generateSuccessResponse(
                HttpStatus.OK,
                "user successfully authorized",
                "user",
                userService.authorize(userAuthorizeRequest)
        );
    }
}
