package com.sixofrods.edtech.controller;

import com.sixofrods.edtech.dto.QuizQuestionDTO;
import com.sixofrods.edtech.entity.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/flashcard-game")
public interface MockController {
    @PostMapping("/create")
    ResponseEntity<Mock> createFlashGame(
            @RequestParam String nameMock,
            @RequestParam Long userId,
            @RequestParam Long languageId,
            @RequestParam int numberOfQuestions,
            @RequestBody List<QuizQuestionDTO> questions
    );

    @PostMapping("/{gameId}/submit")
    ResponseEntity<Boolean> submitAnswer(
            @PathVariable Long gameId,
            @RequestParam Long questionId,
            @RequestParam Long answerId
    );

    @GetMapping("/{gameId}/score")
    ResponseEntity<Integer> getCurrentScore(
            @PathVariable Long gameId
    );

    @GetMapping("/{gameId}")
    ResponseEntity<Mock> getGameById(
            @PathVariable Long gameId
    );

    @DeleteMapping("/{gameId}")
    ResponseEntity<Void> deleteGame(@PathVariable Long gameId);

    @PatchMapping("/{gameId}")
    ResponseEntity<Mock> updateFlashGame(
            @PathVariable Long gameId,
            @RequestParam(required = false) Long languageId,
            @RequestParam(required = false) Integer numberOfQuestions,
            @RequestBody(required = false) List<QuizQuestionDTO> questions
    );
}
