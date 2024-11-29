package com.hamter.mapper;

import org.modelmapper.ModelMapper;

import com.hamter.dto.UserDTO;
import com.hamter.model.User;

public class UserMapper {
	private static final ModelMapper modelMapper = new ModelMapper();

    public static UserDTO toDTO(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setRoleId(user.getRole() != null ? user.getRole().getId() : null);
        return userDTO;
    }

    public static User toEntity(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        return user;
    }
}
