package com.sixofrods.edtech.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@Table(name = "quiz_questions")
@NoArgsConstructor
@AllArgsConstructor
public class QuizQuestions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String question;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("question")
    private List<QuizAnswers> answers = new ArrayList<>();
    @OneToMany(mappedBy = "quizQuestion")
    @JsonIgnoreProperties("quizQuestion")
    private List<QuestionCollection> usages = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "flashcard_game_id")
    @JsonIgnoreProperties({"quizQuestions", "scores", "language", "user"})
    private Mock mock;

}
