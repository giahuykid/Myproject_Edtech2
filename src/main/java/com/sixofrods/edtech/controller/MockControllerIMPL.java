package com.sixofrods.edtech.controller;

import com.sixofrods.edtech.dto.MockDTO;
import com.sixofrods.edtech.dto.QuizQuestionDTO;
import com.sixofrods.edtech.service.MockService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MockControllerIMPL implements MockController {
    @Autowired
    private MockService mockService;

    @Override
    public ResponseEntity<MockDTO> createMock(
            String nameMock,
            Long userId,
            Long languageId,
            int numberOfQuestions,
            List<QuizQuestionDTO> questions) {
        try {
            MockDTO mockDTO = mockService.createMock(nameMock, userId, languageId, numberOfQuestions, questions);
            return ResponseEntity.ok(mockDTO);
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
            boolean result = mockService.submitAnswer(mockId, questionId, answerId);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Integer> getCurrentScore(Long mockId) {
        try {
            int score = mockService.getCurrentScore(mockId);
            return ResponseEntity.ok(score);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<MockDTO> getMockById(Long mockId) {
        try {
            MockDTO mockDTO = mockService.getMockById(mockId);
            return ResponseEntity.ok(mockDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteMock(Long mockId) {
        try {
            mockService.deleteMock(mockId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<MockDTO> updateMock(
            Long mockId,
            Long languageId,
            Integer numberOfQuestions,
            List<QuizQuestionDTO> questions) {
        try {
            MockDTO mockDTO = mockService.updateMock(mockId, languageId, numberOfQuestions, questions);
            return ResponseEntity.ok(mockDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Data
    public class QuizQuestionRequest {
        private List<QuizQuestionDTO> questions;
    }
}
