package com.netcracker.borodin.converter;

import com.netcracker.borodin.dto.UserDTO;
import com.netcracker.borodin.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface UserMapper {
  @Mappings({
    @Mapping(target = "id", source = "entity.id"),
    @Mapping(target = "username", source = "entity.username"),
    @Mapping(target = "state", source = "entity.state"),
    @Mapping(target = "role", source = "entity.role")
  })
  UserDTO userToUserDTO(User entity);

  List<UserDTO> userListToUserDTOList(List<User> list);
}
