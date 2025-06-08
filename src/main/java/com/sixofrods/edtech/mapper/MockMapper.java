package com.sixofrods.edtech.mapper;

import com.sixofrods.edtech.dto.*;
import com.sixofrods.edtech.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MockMapper {
    @Autowired
    private QuizMapper quizMapper;

    public MockDTO toDTO(Mock mock) {
        if (mock == null) return null;
        
        return MockDTO.builder()
                .id(mock.getId())
                .nameMock(mock.getNameMock())
                .numberOfQuestions(mock.getNumberOfQuestions())
                .createdAt(mock.getCreatedAt())
                .languageId(mock.getLanguage().getId())
                .languageName(mock.getLanguage().getLanguageName())
                .userId(mock.getUser().getId())
                .questions(mock.getQuizQuestions().stream()
                        .map(quizMapper::toQuestionDTO)
                        .collect(Collectors.toList()))
                .scores(mock.getScores().stream()
                        .map(this::toScoreDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public QuizQuestionDTO toDTO(QuizQuestions question) {
        return QuizQuestionDTO.builder()
                .id(question.getId())
                .question(question.getQuestion())
                .answers(question.getAnswers().stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public QuizAnswerDTO toDTO(QuizAnswers answer) {
        return QuizAnswerDTO.builder()
                .id(answer.getId())
                .answer(answer.getAnswer())
                .isCorrect(answer.isCorrect())
                .point(answer.getPoint())
                .build();
    }

    public ScoreDTO toScoreDTO(Score score) {
        if (score == null) return null;
        
        return ScoreDTO.builder()
                .id(score.getId())
                .score(score.getScore())
                .submittedAt(score.getSubmittedAt())
                .userId(score.getUser() != null ? score.getUser().getId() : null)
                .mockId(score.getMock() != null ? score.getMock().getId() : null)
                .build();
    }

    public Mock toEntity(MockDTO dto) {
        if (dto == null) return null;
        
        return Mock.builder()
                .id(dto.getId())
                .nameMock(dto.getNameMock())
                .numberOfQuestions(dto.getNumberOfQuestions())
                .createdAt(dto.getCreatedAt())
                .build();
    }

    public QuizQuestions toEntity(QuizQuestionDTO dto) {
        return QuizQuestions.builder()
                .id(dto.getId())
                .question(dto.getQuestion())
                .build();
    }

    public QuizAnswers toEntity(QuizAnswerDTO dto) {
        return QuizAnswers.builder()
                .id(dto.getId())
                .answer(dto.getAnswer())
                .isCorrect(dto.isCorrect())
                .point(dto.getPoint())
                .build();
    }

    public Score toEntity(ScoreDTO dto) {
        return Score.builder()
                .id(dto.getId())
                .score(dto.getScore())
                .submittedAt(dto.getSubmittedAt())
                .build();
    }
} 