package com.elmiraouy.jwtsecurity.security;

import com.elmiraouy.jwtsecurity.Dto.request.AppUserRequestDto;
import com.elmiraouy.jwtsecurity.Dto.response.AppUserResponseDto;
import com.elmiraouy.jwtsecurity.entities.AppRole;
import com.elmiraouy.jwtsecurity.entities.AppUser;
import com.elmiraouy.jwtsecurity.entities.Token;
import com.elmiraouy.jwtsecurity.handlerException.AppUserException;
import com.elmiraouy.jwtsecurity.mappers.AppUserDtoMapper;
import com.elmiraouy.jwtsecurity.repository.AppRoleRepository;
import com.elmiraouy.jwtsecurity.repository.AppUserRepository;
import com.elmiraouy.jwtsecurity.repository.TokenRepository;
import com.elmiraouy.jwtsecurity.service.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final  AppRoleRepository appRoleRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AppUserService appUserService;

    public AppUserResponseDto register(AppUserRequestDto request) throws AppUserException {

        Optional<AppUser> userByEmail = appUserRepository.findByEmail(request.getEmail());
        if(userByEmail.isPresent())
            throw new AppUserException("un utilisateur deja inscrit avec cet email: [%s] :".formatted(request.getEmail()));

        AppRole appRole=appRoleRepository.findByRoleName("SUPPORT").orElseThrow(null);

        Collection<AppRole> appRoles=new ArrayList<>();
        appRoles.add(appRole);

        var user = AppUser.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .appRoles(appRoles)
                .build();
        var jwtToken =jwtService.generateToken(user);
        var jwtRefreshToken = jwtService.generateRefreshToken(user);
        Token token =Token.builder()
                .accessToken(jwtToken)
                .refreshToken(jwtRefreshToken)
                .expired(false)
                .revoked(false)
                .build();
        Token tokenSave=tokenRepository.save(token);
        AppUser saveUser = appUserRepository.save(user);
        appUserService.addTokenToUser(saveUser,tokenSave);

        return appUserService.appUserToDto(saveUser);
    }

    public AppUserResponseDto authenticate(String email,String passWord) throws AppUserException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                email,
                passWord
        ));
        System.out.println("$$$$$$$$$ "+email);
        var user= appUserRepository.findByEmail(email)
                .orElseThrow(() -> new AppUserException(
                        "User With id [%s] not ".formatted(email)));;
        var jwtToken = jwtService.generateToken(user);
        var jwtRefreshToken = jwtService.generateRefreshToken(user);

        // todo  here change old token by new token
        Token token=tokenRepository.findByAppUserId(user.getId());
        token.setRefreshToken(jwtRefreshToken);
        token.setRevoked(false);
        token.setExpired(false);
        token.setAccessToken(jwtToken);
        tokenRepository.save(token);
        System.out.println("  -------------------- Authenticate ");
        return appUserService.appUserToDto(user);
    }

    public AppUserResponseDto refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader =request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail ;
        if(authHeader == null  || !authHeader.startsWith("Bearer ")){
            System.out.println("refresh tok,ne !: Berer est null !!!!");
            return null;
        }
        refreshToken =authHeader.substring(7);
        userEmail= jwtService.extractUserEmail(refreshToken);
        if(userEmail != null ){
            UserDetails userDetails =this.appUserRepository.findByEmail(userEmail).orElseThrow();
            if(jwtService.isTokenValid(refreshToken,userDetails)){
               var accessToken =jwtService.generateToken(userDetails);
               // todo save user in data base
               //revokeAllUserTokens(user)
               //saveUserToken(user,accessToken);

               var authResponse = AppUserResponseDto.builder()
                       .accessToken(accessToken)
                       .refreshToken(refreshToken)
                       .build();
               new ObjectMapper().writeValue(response.getOutputStream(),authResponse);
                System.out.println("refresh token est mis a jour");
               return authResponse;
            }
        }
        System.out.println("refresh null ");
       return null;
    }
}
