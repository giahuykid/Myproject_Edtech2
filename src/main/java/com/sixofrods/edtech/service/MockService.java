package com.sixofrods.edtech.service;

import com.sixofrods.edtech.dto.QuizQuestionDTO;
import com.sixofrods.edtech.entity.Mock;

import java.util.List;

public interface MockService {
    Mock createMock(String nameMock, Long userId, Long languageId, int numberOfQuestions, List<QuizQuestionDTO> questions);

    boolean submitAnswer(Long gameId, Long questionId, Long answerId);

    int getCurrentScore(Long gameId);

    Mock getGameById(Long gameId);

    Mock updateFlashGame(Long gameId, Long languageId, Integer numberOfQuestions, List<QuizQuestionDTO> questions);

    void deleteGame(Long gameId);

}
