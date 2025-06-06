package com.sixofrods.edtech.service;

import com.sixofrods.edtech.dto.MockDTO;
import com.sixofrods.edtech.dto.QuizQuestionDTO;

import java.util.List;

public interface MockService {
    MockDTO createMock(String nameMock, Long userId, Long languageId, int numberOfQuestions, List<QuizQuestionDTO> questions);

    boolean submitAnswer(Long mockId, Long questionId, Long answerId);

    int getCurrentScore(Long mockId);

    MockDTO getMockById(Long mockId);

    MockDTO updateMock(Long mockId, Long languageId, Integer numberOfQuestions, List<QuizQuestionDTO> questions);

    void deleteMock(Long mockId);
    List<MockDTO> getAllMocks(Long userId, Long languageId);
}
