package com.sixofrods.edtech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@Table(name = "quiz_answers")
@NoArgsConstructor
@AllArgsConstructor
public class QuizAnswers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String answer;
    private boolean isCorrect;
    private int point;
    @OneToMany(mappedBy = "quizAnswers")
    private List<StudentAnswer> studentAnswers;
    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private QuizQuestions question;


}
