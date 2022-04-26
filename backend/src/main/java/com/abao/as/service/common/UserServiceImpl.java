package com.abao.as.service.common;

import cn.hutool.core.date.DateUtil;
import com.abao.as.controller.v1.condition.common.UserCondition;
import com.abao.as.dto.mapper.common.UserMapper;
import com.abao.as.dto.model.common.PageDto;
import com.abao.as.dto.model.common.UserDto;
import com.abao.as.exception.type.OwnerException;
import com.abao.as.model.common.User;
import com.abao.as.model.enums.UserRole;
import com.abao.as.repository.common.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.*;


@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDto signup(UserDto userDto) {
        UserRole userRole;
        User user = userRepository.findByUsername(userDto.getUsername());
        if (user == null) {
            user = new User()
                    .setUsername(userDto.getUsername())
                    .setNickname(userDto.getNickname())
                    .setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()))
                    .setRole(UserRole.COMMON)
                    .setCreateTime(new Date())
                    .setUpdateTime(new Date())
                    .setBirth(DateUtil.parse("2000-01-01"))
                    .setMobile(userDto.getMobile());
            return userMapper.toUserDto(userRepository.save(user));
        } else {
            throw new RuntimeException("账号已存在！");
        }
    }


    /**
     * Update User Profile
     *
     * @param userDto
     * @return
     */
//    @Override
//    public UserDto updateProfile(UserDto userDto) {
//        User userModel = userRepository.findByUsername(userDto.getUsername());
//        modelMapper.map(userDto, userModel);
//        return userMapper.toUserDto(userRepository.save(userModel));
//    }


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
            return userMapper.toUserDto(user.get());
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
                .setGender(userDto.getGender())
                .setUpdateTime(new Date())
                .setMobile(userDto.getMobile());
        return userMapper.toUserDto(userRepository.save(userModel));
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
        return userMapper.toUserDto(userRepository.save(userModel));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByPrimaryKey(Long[] primaryKey) {
        for (int i = 0; i < primaryKey.length; i++) {
            userRepository.deleteById(primaryKey[i]);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    // 新增操作
    public UserDto save(UserDto dto) {
        return signup(dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDto getByPrimaryKey(Long primaryKey) {
        return userMapper.toUserDto(userRepository.findById(primaryKey).get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageDto<UserDto> findPageByCondition(UserCondition condition) {
        LinkedList<UserDto> list = new LinkedList<>();
        PageRequest pageable = PageRequest.of(condition.getPageNum(), condition.getPageSize(), Sort.by("createTime"));
        Specification<User> spec = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (!condition.getName().isEmpty()) {
                predicateList.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + condition.getName() + "%"));
            }
            return criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()])).getRestriction();
        };
        Page<User> modelPages = userRepository.findAll(spec, pageable);
        for (User model : modelPages.getContent()) {
            list.add(userMapper.toUserDto(model));
        }
        return new PageDto<UserDto>()
                .setCurrentPage(pageable.getPageNumber())
                .setTotalPage(modelPages.getTotalPages())
                .setData(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDto update(UserDto dto) {
        User model = userRepository.findById(dto.getId()).orElseThrow(
                () -> new OwnerException("修改的用户不存在！")
        );
        model.setBirth(dto.getBirth())
                .setUsername(dto.getUsername())
                .setNickname(dto.getNickname())
                .setUpdateTime(new Date())
                .setEmail(dto.getEmail())
                .setName(dto.getName())
                .setGender(dto.getGender())
                .setMobile(dto.getMobile());
        return modelMapper.map(userRepository.save(model), UserDto.class);
    }
}
