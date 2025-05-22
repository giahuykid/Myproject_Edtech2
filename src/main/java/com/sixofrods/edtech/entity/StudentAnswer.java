package com.sixofrods.edtech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Data
@Builder
@Table(name = "student_answer")
 @NoArgsConstructor
 @AllArgsConstructor
public class StudentAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "class_quiz_id", nullable = false)
    private ClassQuiz classQuiz;

    @ManyToOne
    @JoinColumn(name = "quiz_answer_id")
    private QuizAnswers quizAnswers;
    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private QuizQuestions question;

}
