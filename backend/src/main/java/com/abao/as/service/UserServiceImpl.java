package com.abao.as.service;

import com.abao.as.dto.mapper.UserMapper;
import com.abao.as.dto.model.common.UserDto;
import com.abao.as.model.common.User;
import com.abao.as.model.enums.UserRole;
import com.abao.as.repository.common.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto signup(UserDto userDto) {
        UserRole userRole;
        User user = userRepository.findByUsername(userDto.getUsername());
        if (user == null) {
            user = new User()
                    .setUsername(userDto.getUsername())
                    .setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()))
                    .setRole(UserRole.COMMON)
                    .setMobileNumber(userDto.getMobileNumber());
            return UserMapper.toUserDto(userRepository.save(user));
        } else {
            throw new RuntimeException("账号已存在！");
        }
    }

    /**
     * Search an existing user
     *
     * @param email
     * @return
     */
    @Override
    @Transactional
    public UserDto findUserByUsername(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(email));
        if (user.isPresent()) {
            return modelMapper.map(user.get(), UserDto.class);
        } else {
            return null;
        }
    }

    /**
     * Update User Profile
     *
     * @param userDto
     * @return
     */
    @Override
    public UserDto updateProfile(UserDto userDto) {
        User userModel = userRepository.findByUsername(userDto.getUsername());
        userModel.setName(userDto.getName())
                .setMobileNumber(userDto.getMobileNumber());
        return UserMapper.toUserDto(userRepository.save(userModel));
    }

    /**
     * Change Password
     *
     * @param userDto
     * @param newPassword
     * @return
     */
    @Override
    public UserDto changePassword(UserDto userDto, String newPassword) {
        User userModel = userRepository.findByUsername(userDto.getUsername());
        userModel.setPassword(bCryptPasswordEncoder.encode(newPassword));
        return UserMapper.toUserDto(userRepository.save(userModel));
    }

}
