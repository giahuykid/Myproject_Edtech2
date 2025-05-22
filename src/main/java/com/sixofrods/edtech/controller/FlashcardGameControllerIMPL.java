package com.sixofrods.edtech.controller;

import com.sixofrods.edtech.dto.QuizQuestionDTO;
import com.sixofrods.edtech.entity.FlashcardGame;
import com.sixofrods.edtech.service.FlashcardGameService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class FlashcardGameControllerIMPL implements FlashcardGameController {
    @Autowired
    private FlashcardGameService flashGameService;

    @Override
    public ResponseEntity<FlashcardGame> createFlashGame(
            Long userId,
            Long languageId,
            int numberOfQuestions,
            List<QuizQuestionDTO> questions) {
        try {
            FlashcardGame game = flashGameService.createFlashGame(userId,languageId, numberOfQuestions, questions);
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
            boolean result = flashGameService.submitAnswer(gameId, questionId, answerId);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Integer> getCurrentScore(Long gameId) {
        try {
            int score = flashGameService.getCurrentScore(gameId);
            return ResponseEntity.ok(score);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<FlashcardGame> getGameById(Long gameId) {
        try {
            FlashcardGame game = flashGameService.getGameById(gameId);
            return ResponseEntity.ok(game);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override

    public ResponseEntity<Void> deleteGame(Long gameId) {
        try {
            flashGameService.deleteGame(gameId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<FlashcardGame> updateFlashGame(
            Long gameId,
            Long languageId,
            Integer numberOfQuestions,
            List<QuizQuestionDTO> questions) {
        try {
            FlashcardGame game = flashGameService.updateFlashGame(gameId, languageId, numberOfQuestions, questions);
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
