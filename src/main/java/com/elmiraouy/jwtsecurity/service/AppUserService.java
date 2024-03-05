package com.elmiraouy.jwtsecurity.service;

import com.elmiraouy.jwtsecurity.Dto.request.AppUserRequestDto;
import com.elmiraouy.jwtsecurity.Dto.response.AppUserResponseDto;
import com.elmiraouy.jwtsecurity.entities.AppUser;
import com.elmiraouy.jwtsecurity.entities.Token;
import com.elmiraouy.jwtsecurity.handlerException.AppUserException;

import java.util.List;

public interface AppUserService {
    public List<AppUserResponseDto> findAllUser();
    public AppUserResponseDto findUserByEmail(String email) throws AppUserException;
    public AppUserResponseDto findAppUserById(Long id) throws AppUserException;
    public AppUserResponseDto addUser(AppUserRequestDto userRequestDto);
    public AppUserResponseDto deleteUser(Long id);
    public AppUserResponseDto updateUser(Long id ,AppUserRequestDto userRequestDto) throws AppUserException;
    public void addTokenToUser(AppUser appUser, Token token) throws AppUserException;
}
