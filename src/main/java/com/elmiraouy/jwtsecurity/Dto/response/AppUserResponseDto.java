package com.elmiraouy.jwtsecurity.Dto.response;


import lombok.Builder;

import java.util.List;
@Builder
public record AppUserResponseDto (
         Long id,
         String firstName,
         String lastName,
         String email,
         String accessToken,
         String refreshToken,
         List<String> roles
) {


}
