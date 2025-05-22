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
@Table(name = "class_quiz")
@NoArgsConstructor
@AllArgsConstructor
public class ClassQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String quizName;
    private LocalDateTime duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private Clazz clazz;

    @OneToMany(mappedBy = "classQuiz")
    private List<StudentAnswer> studentAnswers;
    @OneToMany(mappedBy = "classQuiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionCollection> questionCollections = new ArrayList<>();



}
