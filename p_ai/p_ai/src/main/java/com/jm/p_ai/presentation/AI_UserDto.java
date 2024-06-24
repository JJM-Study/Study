package com.jm.p_ai.presentation;

import com.jm.p_ai.domain.AI_User;
import jakarta.validation.constraints.NotNull;

public class AI_UserDto {

    private Long id;

    @NotNull
    private String username;


    public AI_UserDto() {

    }

    public AI_UserDto(String username) {
        this.username = username;
    }

    public AI_UserDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // 527

    public static AI_User toEntity(AI_UserDto ai_userDto) {
        AI_User ai_user = new AI_User();

        ai_user.setId(ai_userDto.getId());
        ai_user.setUsername(ai_userDto.getUsername());

        return ai_user;
    }

    public static AI_UserDto toDto(AI_User ai_user) {
        AI_UserDto ai_userDto = new AI_UserDto(ai_user.getId(),
                                               ai_user.getUsername());

        //ai_userDto.setId(ai_user.getId());

        return ai_userDto;

    }

}