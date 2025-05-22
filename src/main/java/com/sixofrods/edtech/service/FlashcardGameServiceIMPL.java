package com.sixofrods.edtech.service;

import com.sixofrods.edtech.dto.QuizAnswerDTO;
import com.sixofrods.edtech.dto.QuizQuestionDTO;
import com.sixofrods.edtech.entity.*;
import com.sixofrods.edtech.entity.LanguageGame;
import com.sixofrods.edtech.mapper.QuizMapper;
import com.sixofrods.edtech.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlashcardGameServiceIMPL implements FlashcardGameService {
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
    public FlashcardGame createFlashGame(Long userId, Long languageId, int numberOfQuestions, List<QuizQuestionDTO> questions) {
        User user = userRP.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Language language = languageRP.findById(languageId)
                .orElseThrow(() -> new RuntimeException("Language not found"));

        // Find or create LanguageGame for this language
        LanguageGame languageGame = languageGameRP.findByLanguageId(languageId)
                .orElseGet(() -> {
                    LanguageGame newGame = LanguageGame.builder()
                            .gameName(language.getLanguageName() + " Learning Games")
                            .description("Games collection for " + language.getLanguageGames())
                            .duration(LocalDateTime.now())
                            .isActive("Y")
                            .language(language)
                            .build();
                    return languageGameRP.save(newGame);
                });

        // Find or create GameCollection for FLASH_GAME type
        GameColletion gameCollection = languageGame.getGameCollections().stream()
                .filter(gc -> gc.getType() == GameType.FLASH_GAME)
                .findFirst()
                .orElseGet(() -> {
                    GameColletion newCollection = GameColletion.builder()
                            .type(GameType.FLASH_GAME)
                            .createdAt(LocalDateTime.now())
                            .createdBy(user.getFullName())
                            .languageGame(languageGame)
                            .user(user)
                            .flashcardGames(new ArrayList<>()) // initialize list
                            .build();
                    return collectionRP.save(newCollection);
                });

        // Create new FlashcardGame and set its GameCollection
        FlashcardGame game = FlashcardGame.builder()
                .type("FLASH")
                .numberOfQuestions(numberOfQuestions)
                .createdAt(LocalDateTime.now())
                .language(language)
                .quizQuestions(new ArrayList<>())
                .scores(new ArrayList<>())
                .gameCollection(gameCollection) // link to collection
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
                .flashcardGame(game)
                .build();
        score = scoreRP.save(score);
        game.getScores().add(score);

        // Update gameCollection to include this game
        gameCollection.getFlashcardGames().add(game);
        collectionRP.save(gameCollection);

        return flashcardGameRP.save(game);
    }

    @Override
    public boolean submitAnswer(Long gameId, Long questionId, Long answerId) {
        FlashcardGame game = flashcardGameRP.getReferenceById(gameId);
        QuizQuestions question = quizQuestionsRP.getReferenceById(questionId);
        QuizAnswers answer = quizAnswersRP.getReferenceById(answerId);

        if (!question.getFlashcardGame().getId().equals(gameId)) {
            throw new RuntimeException("Question does not belong to this game");
        }

        if (answer.isCorrect()) {
            Score currentScore = game.getScores().stream()
                    .findFirst()
                    .orElseGet(() -> {
                        Score newScore = new Score();
                        newScore.setScore(0);
                        newScore.setSubmittedAt(LocalDateTime.now());
                        newScore.setFlashcardGame(game);
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
        FlashcardGame game = flashcardGameRP.getReferenceById(gameId);
        return game.getScores().stream()
                .findFirst()
                .map(Score::getScore)
                .orElse(0);
    }

    @Override
    public FlashcardGame getGameById(Long gameId) {
        return flashcardGameRP.getReferenceById(gameId);
    }

    @Override
    public FlashcardGame updateFlashGame(Long gameId, Long languageId, Integer numberOfQuestions, List<QuizQuestionDTO> questions) {
        FlashcardGame existingGame = flashcardGameRP.findById(gameId)
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
        FlashcardGame game = flashcardGameRP.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));
        flashcardGameRP.delete(game);
    }

    private QuizQuestions createQuizQuestion(QuizQuestionDTO questionDto, FlashcardGame game) {
        QuizMapper mapper = new QuizMapper();
        QuizQuestions question = mapper.toQuestionEntity(questionDto);
        question.setFlashcardGame(game);
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