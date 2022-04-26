package com.abao.as.dto.mapper.common;

import com.abao.as.controller.v1.request.common.UserRequest;
import com.abao.as.dto.model.common.UserDto;
import com.abao.as.model.common.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Arpit Khandelwal.
 */
@Component
public class UserMapper {

    @Autowired
    private ModelMapper modelMapper;

    public UserDto toUserDto(UserRequest user) {
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto toUserDto(User model) {
        return modelMapper.map(model, UserDto.class);
    }
}
