package com.wallet_service.application.in.service;

import com.wallet_service.domain.dto.JwtDTO;
import com.wallet_service.domain.dto.JwtMapper;
import com.wallet_service.domain.model.Jwt;

/**
 * Access token generation and validation class.
 */
public class JWTService {

    private Jwt jwt;

    public JWTService() {
        jwt = new Jwt();
    }

    public boolean validateJWT(String authorizationHeader) {
        return false;
    }

    public JwtDTO getJwtDTO(String login) {
        jwt.createJWT(login);
        return JwtMapper.INSTANCE.jwtToJwtDTO(jwt);
    }
}
