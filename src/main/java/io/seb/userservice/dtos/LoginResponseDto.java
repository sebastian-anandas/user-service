package io.seb.userservice.dtos;

import io.seb.userservice.models.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {

    private Token token;

}
