package com.sixofrods.edtech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@Table(name = "flash_card_game_colletion")
@NoArgsConstructor
@AllArgsConstructor
public class GameColletion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    @Enumerated(EnumType.STRING)
    private GameType type;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;



    @ManyToOne
    @JoinColumn(name = "language_game_id")
    private LanguageGame languageGame;
}
