package com.example.weather.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sessions")
public class UserSession {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String guid;

    @ManyToOne
    @JoinColumn(name = "client", nullable = false)
    private User user;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

}
