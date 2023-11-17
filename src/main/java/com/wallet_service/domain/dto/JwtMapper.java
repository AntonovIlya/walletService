package com.wallet_service.domain.dto;

import com.wallet_service.domain.model.Jwt;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JwtMapper {
    //todo добавить документацию
    JwtMapper INSTANCE = Mappers.getMapper(JwtMapper.class);
    JwtDTO jwtToJwtDTO(Jwt jwt);
}
