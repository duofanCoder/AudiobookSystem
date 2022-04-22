package com.abao.as.dto.mapper;

import com.abao.as.model.common.User;
import com.abao.as.dto.model.common.UserDto;
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

    public UserDto toUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

}
