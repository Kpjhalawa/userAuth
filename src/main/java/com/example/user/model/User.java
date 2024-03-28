package com.example.user.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User extends BaseModel{
private String name;
private String email;
private String password;
private boolean isEmailVerified;
@ManyToMany
private List<Role> roles;

public User(){
}
public User(String name,String email,String password){
    this.name = name;
    this.email = email;
    this.password = password;
    this.isEmailVerified = false;
    this.roles = new ArrayList<>();
}

}
