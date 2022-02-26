package recipes.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import recipes.user.dto.UserDto;
import recipes.user.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/register")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto) {

        UserDto userSaved = userService.register(userDto);

         return ResponseEntity.ok(userSaved);

    }

}
