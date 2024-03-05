package com.elmiraouy.jwtsecurity.mappers;

import com.elmiraouy.jwtsecurity.Dto.request.AppUserRequestDto;
import com.elmiraouy.jwtsecurity.Dto.response.AppUserResponseDto;
import com.elmiraouy.jwtsecurity.entities.AppRole;
import com.elmiraouy.jwtsecurity.entities.AppUser;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AppUserDtoMapper implements Function<AppUser, AppUserResponseDto> {

    @Override
    public AppUserResponseDto apply(AppUser appUser) {
        return new AppUserResponseDto(
                appUser.getId(),
                appUser.getFirstName(),
                appUser.getLastName(),
                appUser.getEmail(),
                appUser.getAddress(),
                appUser.getTelephone(),
                appUser.getVille()
        );
    }

    public AppUserResponseDto appUserToDto(AppUser appUser){
        return  new AppUserResponseDto(
                appUser.getId(),
                appUser.getFirstName(),
                appUser.getLastName(),
                appUser.getEmail(),
                appUser.getToken().getAccessToken(),
                appUser.getToken().getRefreshToken(),
                appUser.getAddress(),
                appUser.getTelephone(),
                appUser.getVille(),
                appUser.getAppRoles()
                        .stream()
                        .map(AppRole::getRoleName)
                        .collect(Collectors.toList())
        );
    }
}
