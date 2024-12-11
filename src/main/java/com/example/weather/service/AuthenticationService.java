package com.example.weather.service;

import com.example.weather.dto.LoginDto;
import com.example.weather.dto.RegistrationDto;
import com.example.weather.entity.User;
import com.example.weather.exception.CredentialsException;
import com.example.weather.exception.UserExistsException;
import com.example.weather.exception.UserNotFoundExcepiton;
import com.example.weather.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final SessionService sessionService;

    public void register(RegistrationDto dto) {
        try {
            repository.save(buildUser(dto.getUsername(), dto.getPassword()));
        } catch (HibernateException e) {
           throw new UserExistsException("User with this username already exists");
        }
    }

    public String logIn(LoginDto dto){
        User user = repository.findByUsername(dto.getUsername()).orElseThrow(() -> new UserNotFoundExcepiton("User was not found"));

        String hashedPassword = hashPassword(dto.getPassword());

        if (user.getPassword().equals(hashedPassword)){
            return sessionService.startSession(user);
        }else {
            throw new CredentialsException("Password in incorrect");
        }

    }

    private String hashPassword(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());

            return Base64.getEncoder().encodeToString(hash);
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException();
        }
    }


    private User buildUser(String username,
                           String password) {
        return User.builder()
                .username(username)
                .password(hashPassword(password))
                .build();
    }

}
