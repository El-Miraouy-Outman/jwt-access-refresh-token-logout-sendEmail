package com.elmiraouy.jwtsecurity.handlerException;

import lombok.*;

import java.util.Set;
@Data

@RequiredArgsConstructor
public class ObjectNotValidException extends RuntimeException {

    private final Set<String> errorMessage;
}
