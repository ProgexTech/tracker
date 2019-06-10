package com.progex.tracker.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private long id;
    private List<String> roles;
    private String name;
    private String email;
    private String phone;
    private String address;

  /*  public static UserDto toDto(User userEntity) {
        UserDto user = new UserDto();

        user.setId(userEntity.getId());
        user.setName(userEntity.getName());
        user.setPhone(userEntity.getPhone());
        user.setAddress(userEntity.getAddress());
        user.setRoles(userEntity.getRoles());

        return user;
    }
*/
   /* public static List<UserDto> toDto(List<User> userEntities) {
        return userEntities.stream()
                .map(UserDto::toDto)
                .collect(Collectors.toList());
    }*/

}
