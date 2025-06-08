package com.sixofrods.edtech.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@Table(name = "language_game")
@NoArgsConstructor
@AllArgsConstructor
public class LanguageGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gameName;
    private String description;
    private LocalDateTime duration;
    private String isActive;
    @ManyToOne
    @JoinColumn(name = "level_id", nullable = false)
    private Level level;
    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;
    @OneToMany(mappedBy = "languageGame")
    private List<GameColletion> gameCollections = new ArrayList<>();

}
