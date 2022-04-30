package lv.gennadyyonov.spring.jpa.multiple.db.service;

import lombok.RequiredArgsConstructor;
import lv.gennadyyonov.spring.jpa.multiple.db.dao.user.UserRepository;
import lv.gennadyyonov.spring.jpa.multiple.db.dto.user.UserDto;
import lv.gennadyyonov.spring.jpa.multiple.db.model.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lv.gennadyyonov.spring.jpa.multiple.db.config.DatabaseConstants.USER_TRANSACTION_MANAGER_NAME;

@Transactional(USER_TRANSACTION_MANAGER_NAME)
@RequiredArgsConstructor
@Service
public class UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public UserDto create(UserDto dto) {
        User newUser = modelMapper.map(dto, User.class);
        User savedUser = userRepository.save(newUser);
        return modelMapper.map(savedUser, UserDto.class);
    }

    public UserDto getById(Long id) {
        User user = userRepository.getById(id);
        return modelMapper.map(user, UserDto.class);
    }
}
