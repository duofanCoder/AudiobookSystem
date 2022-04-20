package com.abao.as.dto.mapper;

import com.abao.as.model.common.User;
import com.abao.as.dto.model.common.UserDto;
import org.springframework.stereotype.Component;

/**
 * Created by Arpit Khandelwal.
 */
@Component
public class UserMapper {

    public static UserDto toUserDto(User user) {
        return new UserDto()
                .setUsername(user.getUsername())
                .setMobileNumber(user.getMobileNumber())
                .setRole(user.getRole());
    }

}
