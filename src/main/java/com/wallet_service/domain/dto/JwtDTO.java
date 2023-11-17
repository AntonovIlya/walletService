package com.wallet_service.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtDTO {

    //todo добавить документацию
    private String accessToken;
    private String tokenType;
}
