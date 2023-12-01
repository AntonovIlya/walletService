package com.wallet_service.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtDTO {

    /**
     * Encoded token.
     */
    private String accessToken;
    /**
     * Web-marker JWT in header of HTTP request.
     */
    private String tokenType;
}
