package com.sixofrods.edtech.controller;

import com.sixofrods.edtech.dto.QuizQuestionDTO;
import com.sixofrods.edtech.entity.Mock;
import com.sixofrods.edtech.service.MockService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class MockControllerIMPL implements MockController {
    @Autowired
    private MockService MockService;

    @Override
    public ResponseEntity<Mock> createMock(
            String nameMock,
            Long userId,
            Long languageId,
            int numberOfQuestions,
            List<QuizQuestionDTO> questions) {
        try {
            Mock game = MockService.createMock(nameMock,userId,languageId, numberOfQuestions, questions);
            return ResponseEntity.ok(game);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Boolean> submitAnswer(
            Long mockId,
            Long questionId,
            Long answerId) {
        try {
            boolean result = MockService.submitAnswer(mockId, questionId, answerId);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Integer> getCurrentScore(Long mockId) {
        try {
            int score = MockService.getCurrentScore(mockId);
            return ResponseEntity.ok(score);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Mock> getMockById(Long mockId) {
        try {
            Mock game = MockService.getMockById(mockId);
            return ResponseEntity.ok(game);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override

    public ResponseEntity<Void> deleteMock(Long mockId) {
        try {
            MockService.deleteMock(mockId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Mock> updateMock(
            Long mockId,
            Long languageId,
            Integer numberOfQuestions,
            List<QuizQuestionDTO> questions) {
        try {
            Mock game = MockService.updateMock(mockId, languageId, numberOfQuestions, questions);
            return ResponseEntity.ok(game);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @Data
    public class QuizQuestionRequest {
        private List<QuizQuestionDTO> questions;
    }
}
