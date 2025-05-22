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
@Table(name = "flash_card_game")
@NoArgsConstructor
@AllArgsConstructor
public class FlashcardGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private int numberOfQuestions;
    private LocalDateTime createdAt;
    private String createdBy;
    @ManyToOne
    @JoinColumn(name = "game_collection_id")
    private GameColletion gameCollection;
    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;
    @OneToMany(mappedBy = "flashcardGame", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizQuestions> quizQuestions = new ArrayList<>();
    @OneToMany(mappedBy = "flashcardGame", cascade = CascadeType.ALL)
    private List<Score> scores = new ArrayList<>();


}
