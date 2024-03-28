package com.example.user.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
private String email;
private String password;
private String name;
}
