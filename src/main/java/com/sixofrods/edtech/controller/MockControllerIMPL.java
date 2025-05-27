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
    public ResponseEntity<Mock> createFlashGame(
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
            Long gameId,
            Long questionId,
            Long answerId) {
        try {
            boolean result = MockService.submitAnswer(gameId, questionId, answerId);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Integer> getCurrentScore(Long gameId) {
        try {
            int score = MockService.getCurrentScore(gameId);
            return ResponseEntity.ok(score);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Mock> getGameById(Long gameId) {
        try {
            Mock game = MockService.getGameById(gameId);
            return ResponseEntity.ok(game);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override

    public ResponseEntity<Void> deleteGame(Long gameId) {
        try {
            MockService.deleteGame(gameId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Mock> updateFlashGame(
            Long gameId,
            Long languageId,
            Integer numberOfQuestions,
            List<QuizQuestionDTO> questions) {
        try {
            Mock game = MockService.updateFlashGame(gameId, languageId, numberOfQuestions, questions);
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
