package com.sixofrods.edtech.service;

import com.sixofrods.edtech.dto.QuizQuestionDTO;
import com.sixofrods.edtech.entity.FlashcardGame;

import java.util.List;

public interface FlashcardGameService {
    FlashcardGame createFlashGame(Long userId, Long languageId, int numberOfQuestions, List<QuizQuestionDTO> questions);

    boolean submitAnswer(Long gameId, Long questionId, Long answerId);

    int getCurrentScore(Long gameId);

    FlashcardGame getGameById(Long gameId);

    FlashcardGame updateFlashGame(Long gameId, Long languageId, Integer numberOfQuestions, List<QuizQuestionDTO> questions);

    void deleteGame(Long gameId);
}
