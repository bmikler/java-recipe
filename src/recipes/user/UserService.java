package recipes.user;

import org.springframework.stereotype.Service;

import javax.persistence.SecondaryTable;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDtoMapper userDtoMapper;

    public UserService(UserRepository userRepository, UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.userDtoMapper = userDtoMapper;
    }

    public UserDto register(UserDto userDto) {

        if(userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistException();
        }

        User user = userDtoMapper.map(userDto);
        User userSaved = userRepository.save(user);

        return userDtoMapper.map(userSaved);
    }
}
