package recipes.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class UserDtoMapper {

    private final PasswordEncoder passwordEncoder;

    public UserDtoMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    User map(UserDto user) {
        return new User(
          user.getEmail(),
          passwordEncoder.encode(user.getPassword())
        );
    }

    UserDto map(User user){

        return new UserDto(
                user.getEmail(),
                user.getPassword()
        );

    }

}
