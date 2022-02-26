package recipes.user.dto;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import recipes.user.User;

@Component
public class UserDtoMapper {

    private final PasswordEncoder passwordEncoder;

    public UserDtoMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User map(UserDto user) {
        return new User(
                user.getEmail(),
                passwordEncoder.encode(user.getPassword())
        );
    }

    public UserDto map(User user){

        return new UserDto(
                user.getEmail(),
                user.getPassword()
        );

    }

}
