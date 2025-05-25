package com.sixofrods.edtech.service;

import com.sixofrods.edtech.dto.QuizAnswerDTO;
import com.sixofrods.edtech.dto.QuizQuestionDTO;
import com.sixofrods.edtech.entity.*;
import com.sixofrods.edtech.mapper.QuizMapper;
import com.sixofrods.edtech.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MockServiceIMPL implements MockService {
    @Autowired
    private FlashcardGameRP flashcardGameRP;
    @Autowired
    private QuizQuestionsRP quizQuestionsRP;
    @Autowired
    private QuizAnswersRP quizAnswersRP;
    @Autowired
    private LanguageRP languageRP;
    @Autowired
    private ScoreRP scoreRP;
    @Autowired
    private UserRP userRP;
    @Autowired
    private GameColletionRP collectionRP;
    @Autowired
    private LanguageGameRP languageGameRP;

    @Override
    public Mock createMock(String nameMock, Long userId, Long languageId, int numberOfQuestions, List<QuizQuestionDTO> questions) {
        User user = userRP.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Language language = languageRP.findById(languageId)
                .orElseThrow(() -> new RuntimeException("Language not found"));





        // Create new FlashcardGame and set its GameCollection
        Mock game = Mock.builder()
                .nameMock(nameMock)
                .numberOfQuestions(numberOfQuestions)
                .createdAt(LocalDateTime.now())
                .language(language)
                .quizQuestions(new ArrayList<>())
                .scores(new ArrayList<>())
                .build();
        game = flashcardGameRP.save(game);

        // Add questions and answers
        for (QuizQuestionDTO questionDto : questions) {
            QuizQuestions question = createQuizQuestion(questionDto, game);
            game.getQuizQuestions().add(question);
        }

        // Create initial score
        Score score = Score.builder()
                .score(0)
                .submittedAt(LocalDateTime.now())
                .mock(game)
                .build();
        score = scoreRP.save(score);
        game.getScores().add(score);



        return flashcardGameRP.save(game);
    }

    @Override
    public boolean submitAnswer(Long gameId, Long questionId, Long answerId) {
        Mock game = flashcardGameRP.getReferenceById(gameId);
        QuizQuestions question = quizQuestionsRP.getReferenceById(questionId);
        QuizAnswers answer = quizAnswersRP.getReferenceById(answerId);

        if (!question.getMock().getId().equals(gameId)) {
            throw new RuntimeException("Question does not belong to this game");
        }

        if (answer.isCorrect()) {
            Score currentScore = game.getScores().stream()
                    .findFirst()
                    .orElseGet(() -> {
                        Score newScore = new Score();
                        newScore.setScore(0);
                        newScore.setSubmittedAt(LocalDateTime.now());
                        newScore.setMock(game);
                        game.getScores().add(newScore);
                        return newScore;
                    });

            currentScore.setScore(currentScore.getScore() + answer.getPoint());
            scoreRP.save(currentScore);
            return true;
        }
        return false;
    }

    @Override
    public int getCurrentScore(Long gameId) {
        Mock game = flashcardGameRP.getReferenceById(gameId);
        return game.getScores().stream()
                .findFirst()
                .map(Score::getScore)
                .orElse(0);
    }

    @Override
    public Mock getGameById(Long gameId) {
        return flashcardGameRP.getReferenceById(gameId);
    }

    @Override
    public Mock updateFlashGame(Long gameId, Long languageId, Integer numberOfQuestions, List<QuizQuestionDTO> questions) {
        Mock existingGame = flashcardGameRP.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        if (languageId != null) {
            Language language = languageRP.findById(languageId)
                    .orElseThrow(() -> new RuntimeException("Language not found"));
            existingGame.setLanguage(language);
        }

        if (numberOfQuestions != null) {
            existingGame.setNumberOfQuestions(numberOfQuestions);
        }

        if (questions != null && !questions.isEmpty()) {
            // Clear existing questions
            existingGame.getQuizQuestions().clear();

            // Add new questions using the updated createQuizQuestion method
            for (QuizQuestionDTO questionDTO : questions) {
                QuizQuestions quizQuestion = createQuizQuestion(questionDTO, existingGame);
                existingGame.getQuizQuestions().add(quizQuestion);
            }
        }

        return flashcardGameRP.save(existingGame);
    }

    @Override
    public void deleteGame(Long gameId) {
        Mock game = flashcardGameRP.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));
        flashcardGameRP.delete(game);
    }

    private QuizQuestions createQuizQuestion(QuizQuestionDTO questionDto, Mock game) {
        QuizMapper mapper = new QuizMapper();
        QuizQuestions question = mapper.toQuestionEntity(questionDto);
        question.setMock(game);
        question.setAnswers(new ArrayList<>());
        question = quizQuestionsRP.save(question);

        for (QuizAnswerDTO answerDto : questionDto.getAnswers()) {
            QuizAnswers answer = mapper.toAnswerEntity(answerDto);
            answer.setQuestion(question);
            answer = quizAnswersRP.save(answer);
            question.getAnswers().add(answer);
        }

        return question;
    }
}