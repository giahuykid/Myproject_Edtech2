package com.sixofrods.edtech.controller;

import com.sixofrods.edtech.dto.MockDTO;
import com.sixofrods.edtech.dto.QuizQuestionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/mock")
public interface MockController {
    @PostMapping("/create")
    ResponseEntity<MockDTO> createMock(
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

    @GetMapping("getMock/{mockId}")
    ResponseEntity<MockDTO> getMockById(
            @PathVariable Long mockId
    );

    @DeleteMapping("/delete/{mockId}")
    ResponseEntity<Void> deleteMock(@PathVariable Long mockId);

    @PatchMapping("/update/{mockId}")
    ResponseEntity<MockDTO> updateMock(
            @PathVariable Long mockId,
            @RequestParam(required = false) Long languageId,
            @RequestParam(required = false) Integer numberOfQuestions,
            @RequestBody(required = false) List<QuizQuestionDTO> questions
    );
    @GetMapping("/all")
    ResponseEntity<List<MockDTO>> getAllMocks(
        @RequestParam(required = false) Long userId,
        @RequestParam(required = false) Long languageId
    );
}
