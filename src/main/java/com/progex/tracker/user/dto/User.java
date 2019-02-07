package com.progex.tracker.user.dto;

import com.progex.tracker.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private long id;
    private List<String> roles;
    private String name;
    private String email;
    private String phone;
    private String address;

    public static User toDto(UserEntity userEntity) {
        User user = new User();

        user.setId(userEntity.getId());
        user.setName(userEntity.getName());
        user.setPhone(userEntity.getPhone());
        user.setAddress(userEntity.getAddress());
        user.setRoles(userEntity.getRoles());

        return user;
    }

    public static List<User> toDto(List<UserEntity> userEntities) {
        return userEntities.stream()
                .map(User::toDto)
                .collect(Collectors.toList());
    }

    public UserEntity toEntity() {
        UserEntity userEntity = new UserEntity();

        userEntity.setId(this.id);
        userEntity.setName(this.name);
        userEntity.setPhone(this.phone);
        userEntity.setAddress(this.address);
        userEntity.setRoles(this.roles);

        return userEntity;
    }

}
