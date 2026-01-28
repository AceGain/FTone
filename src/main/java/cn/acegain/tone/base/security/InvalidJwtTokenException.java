package cn.acegain.tone.base.security;

import org.springframework.security.core.AuthenticationException;

import java.io.Serial;

public class InvalidJwtTokenException extends AuthenticationException {

    @Serial
    private static final long serialVersionUID = -1L;

    public InvalidJwtTokenException(String message) {
        super(message);
    }

}
