package com.sixofrods.edtech.controller;

import com.sixofrods.edtech.dto.QuizQuestionDTO;
import com.sixofrods.edtech.entity.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/mock")
public interface MockController {
    @PostMapping("/create")
    ResponseEntity<Mock> createMock(
            @RequestParam String nameMock,
            @RequestParam Long userId,
            @RequestParam Long languageId,
            @RequestParam int numberOfQuestions,
            @RequestBody List<QuizQuestionDTO> questions
    );

    @PostMapping("/{mockId}/submit")
    ResponseEntity<Boolean> submitAnswer(
            @PathVariable Long mockId,
            @RequestParam Long questionId,
            @RequestParam Long answerId
    );

    @GetMapping("/{mockId}/score")
    ResponseEntity<Integer> getCurrentScore(
            @PathVariable Long mockId
    );

    @GetMapping("/{mockId}")
    ResponseEntity<Mock> getMockById(
            @PathVariable Long mockId
    );

    @DeleteMapping("/{mockId}")
    ResponseEntity<Void> deleteMock(@PathVariable Long mockId);

    @PatchMapping("/{mockId}")
    ResponseEntity<Mock> updateMock(
            @PathVariable Long mockId,
            @RequestParam(required = false) Long languageId,
            @RequestParam(required = false) Integer numberOfQuestions,
            @RequestBody(required = false) List<QuizQuestionDTO> questions
    );
}
