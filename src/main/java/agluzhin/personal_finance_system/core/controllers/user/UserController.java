package agluzhin.personal_finance_system.core.controllers.user;

import agluzhin.personal_finance_system.core.models.user.UserToCreate;
import agluzhin.personal_finance_system.core.models.user.UserToSetActive;
import agluzhin.personal_finance_system.core.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam("userId") String userId
    ) {
        return userService.getById(userId);
    }

    @GetMapping("/getItems")
    public ResponseEntity<?> getItems() {
        return userService.getItems();
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(
            @RequestBody UserToCreate userToCreate
    ) {
        return userService.create(userToCreate);
    }

    @PostMapping("/setIsActive")
    public ResponseEntity<?> setIsActive(
            @RequestBody UserToSetActive userToSetActive
    ) {
        return userService.setIsActive(userToSetActive);
    }
}
