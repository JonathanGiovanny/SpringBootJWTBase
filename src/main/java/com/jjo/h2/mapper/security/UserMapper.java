package com.jjo.h2.mapper.security;

import org.mapstruct.Mapper;
import com.jjo.h2.dto.security.SingUpDTO;
import com.jjo.h2.dto.security.UserDTO;
import com.jjo.h2.model.security.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

  User singUpToUser(SingUpDTO singUp);

  User dtoToEntity(UserDTO userDto);

  UserDTO entityToDto(User user);
}
