package com.example.user.services;

import com.example.user.dto.LoginRequestDto;
import com.example.user.model.Token;
import com.example.user.model.User;
import com.example.user.repository.TokenRepository;
import com.example.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ser.std.CalendarSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private TokenRepository tokenRepository;

    public User signUp(String name,String email,String password){
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        return userRepository.save(user);
    }

    // for login gives token

    public Token login(String email,String password){
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new RuntimeException("Invalid User or Password");
        }
        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("Invalid User or Password");
        }
        Token token = new Token();
        token.setUser(user);
        token.setValue(UUID.randomUUID().toString());
        Date expiredDate = getExpriedDate();

        token.setExpireAt(expiredDate);

        return tokenRepository.save(token);
    }

    private Date getExpriedDate() {
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(new Date());

        //add(Calendar.DAY_OF_MONTH,-5) this is for add date or sub
        calendarDate.add(Calendar.DAY_OF_MONTH ,30);
        Date expiredDate = calendarDate.getTime();
        return expiredDate;
    }

    public void logout(String token){
        Optional<Token> tokenOptional = tokenRepository.findByValueAndDeletedEquals(token,false);
        if(tokenOptional.isEmpty()){
            throw new RuntimeException("Token is Invalid");
        }
        Token tokenObject = tokenOptional.get();
        tokenObject.setDeleted(true);

        tokenRepository.save(tokenObject);
    }

    public boolean validateToken(String token){
        /*
        To validated token we check three condition
        1.check if token value is present
        2.check if token is not deleted
        3.check if token is not expired
         */

        Optional<Token> tokenOptional = tokenRepository.findByValueAndDeletedEqualsAndExpireAtGreaterThan(
                token,false,new Date());
        if(tokenOptional.isEmpty()){
            return false;
        }
        return true;
    }

}
