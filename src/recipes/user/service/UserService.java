package recipes.user.service;

import org.springframework.stereotype.Service;
import recipes.user.User;
import recipes.user.UserRepository;
import recipes.user.dto.UserDto;
import recipes.user.dto.UserDtoMapper;

import static recipes.security.Role.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    public UserService(UserRepository userRepository, UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
    }

    public UserDto register(UserDto userDto) {

        if(!userDto.getEmail().endsWith(".com")) {
            throw new WrongEmailException();
        }

        if(userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistException();
        }

        User user = userDtoMapper.map(userDto);
        user.setRole(USER);
        User userSaved = userRepository.save(user);

        return userDtoMapper.map(userSaved);
    }


}
