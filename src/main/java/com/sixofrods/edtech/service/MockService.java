package com.sixofrods.edtech.service;

import com.sixofrods.edtech.dto.QuizQuestionDTO;
import com.sixofrods.edtech.entity.Mock;

import java.util.List;

public interface MockService {
    Mock createMock(String nameMock, Long userId, Long languageId, int numberOfQuestions, List<QuizQuestionDTO> questions);

    boolean submitAnswer(Long mockId, Long questionId, Long answerId);

    int getCurrentScore(Long mockId);

    Mock getMockById(Long mockId);

    Mock updateMock(Long mockId, Long languageId, Integer numberOfQuestions, List<QuizQuestionDTO> questions);

    void deleteMock(Long mockId);

}
