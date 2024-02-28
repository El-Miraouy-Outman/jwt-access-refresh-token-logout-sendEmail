package com.elmiraouy.jwtsecurity.Dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder

public class AppUserRequestDto
{

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    List<String> roles;
}
