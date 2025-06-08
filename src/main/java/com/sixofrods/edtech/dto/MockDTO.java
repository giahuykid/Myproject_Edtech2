package com.sixofrods.edtech.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MockDTO {
    private Long id;
    private String nameMock;
    private int numberOfQuestions;
    private LocalDateTime createdAt;
    private Long languageId;
    private String languageName;
    private Long userId;
    private List<QuizQuestionDTO> questions;
    private List<ScoreDTO> scores;
} 