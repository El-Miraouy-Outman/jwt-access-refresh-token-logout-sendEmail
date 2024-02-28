package com.elmiraouy.jwtsecurity.service;

import com.elmiraouy.jwtsecurity.Dto.request.AppUserRequestDto;
import com.elmiraouy.jwtsecurity.Dto.response.AppUserResponseDto;
import com.elmiraouy.jwtsecurity.entities.AppRole;
import com.elmiraouy.jwtsecurity.entities.AppUser;
import com.elmiraouy.jwtsecurity.entities.Token;
import com.elmiraouy.jwtsecurity.handlerException.AppUserException;
import com.elmiraouy.jwtsecurity.mappers.AppUserDtoMapper;
import com.elmiraouy.jwtsecurity.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final AppUserDtoMapper appUserDtoMapper;
    private final TokenService tokenService;
    @Override
    public List<AppUserResponseDto> findAllUser() {
        List<AppUser> users=appUserRepository.findAll();
        return users.stream().map(appUserDtoMapper).toList();

    }

    @Override
    public AppUserResponseDto findUserByEmail(String email) throws AppUserException {
        AppUser user=appUserRepository.findByEmail(email)
                .orElseThrow(() -> new AppUserException(
                        "User With id [%s] not ".formatted(email)));
        return appUserToDto(user);
    }
    @Override
    public AppUserResponseDto findAppUserById(Long id) throws AppUserException {
        AppUser appUser=appUserRepository.findAppUserById(id)
                .orElseThrow(() -> new AppUserException(
                        "User With id [%s] not ".formatted(id)));
       return appUserToDto(appUser);

    }

    @Override
    public AppUserResponseDto addUser(AppUserRequestDto userRequestDto) {
        return null;
    }

    @Override
    public AppUserResponseDto deleteUser(Long id) {
        return null;
    }

    @Override
    public AppUserResponseDto updateUser(Long id, AppUserRequestDto userRequestDto) throws AppUserException {
        AppUser user=appUserRepository.findById(id)
                .orElseThrow(() -> new AppUserException(
                        "User With id [%s] not ".formatted(id)));
        return null;
    }

    @Override
    public void addTokenToUser(AppUser appUser, Token token) throws AppUserException {

        AppUser appUser1=appUserRepository.findByEmail(appUser.getEmail()).orElseThrow(() -> new AppUserException(
                "User With Email: [%s] not ".formatted(appUser.getEmail())));
        appUser1.setToken(token);
        appUserRepository.save(appUser1);
    }
    @Override
    public AppUserResponseDto appUserToDto(AppUser appUser){
        return  new AppUserResponseDto(
                appUser.getId(),
                appUser.getFirstName(),
                appUser.getLastName(),
                appUser.getEmail(),
                appUser.getToken().getAccessToken(),
                appUser.getToken().getRefreshToken(),
                appUser.getAppRoles()
                        .stream()
                        .map(AppRole::getRoleName)
                        .collect(Collectors.toList())
        );
    }

}
