package com.sixofrods.edtech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "question_collection")
@NoArgsConstructor
@AllArgsConstructor
public class QuestionCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long numberOfQuestions;
    @ManyToOne
    @JoinColumn(name = "quiz_question_id", nullable = false)
    private QuizQuestions quizQuestion;

    @ManyToOne
    @JoinColumn(name = "class_quiz_id", nullable = false)
    private ClassQuiz classQuiz;


}
