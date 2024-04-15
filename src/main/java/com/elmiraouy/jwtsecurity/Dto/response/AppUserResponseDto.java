package com.elmiraouy.jwtsecurity.Dto.response;


import com.elmiraouy.jwtsecurity.entities.Comment;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;
@Builder
@Data

public class AppUserResponseDto {
    private Long id;
    private  String firstName;
    private  String lastName;
    private  String email;
    private  String accessToken;
    private  String refreshToken;
    private  String address;
    private  String telephone;
    private  String ville;
    private  String roles;
    private Collection<Comment> comments;

    public AppUserResponseDto(Long id, String firstName, String lastName, String email, String accessToken, String refreshToken,
                              String address, String telephone, String ville, String roles,Collection<Comment> comments) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.address = address;
        this.telephone = telephone;
        this.ville = ville;
        this.roles = roles;
        this.comments=comments;
    }
    public AppUserResponseDto(Long id, String firstName, String lastName, String email,
                              String address, String telephone, String ville,Collection<Comment> comments) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.telephone = telephone;
        this.ville = ville;

        this.comments=comments;
    }


    public AppUserResponseDto(Long id, String firstName, String lastName,
                              String email, String address, String telephone, String ville,String roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.telephone = telephone;
        this.ville = ville;
        this.roles=roles;
    }

}
