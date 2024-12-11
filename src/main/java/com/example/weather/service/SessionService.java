package com.example.weather.service;


import com.example.weather.entity.User;
import com.example.weather.entity.UserSession;
import com.example.weather.exception.UserNotFoundExcepiton;
import com.example.weather.repository.UserSessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class SessionService {

    private final UserSessionRepository userSessionRepository;

    public String startSession(User user){
        return createSession(user);
    }

    public User getSessionUser(String guid){
        Optional<UserSession> userSession = userSessionRepository.findByGuid(guid);

        if (userSession.isPresent()){
            return userSession.get().getUser();
        }else {
            throw new UserNotFoundExcepiton("User was not found");
        }
    }

    private String createSession(User user){
        String guid = UUID.randomUUID().toString();
        userSessionRepository.save(buildUserSession(guid, user));

        return guid;
    }


    private UserSession buildUserSession(String guid, User user){
        return UserSession.builder()
                .guid(guid)
                .user(user)
                .expiresAt(LocalDateTime.now().plusMinutes(60))
                .build();
    }

}
