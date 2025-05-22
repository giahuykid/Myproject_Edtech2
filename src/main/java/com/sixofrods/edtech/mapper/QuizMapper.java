package com.sixofrods.edtech.mapper;

import com.sixofrods.edtech.dto.QuizAnswerDTO;
import com.sixofrods.edtech.dto.QuizQuestionDTO;
import com.sixofrods.edtech.entity.QuizAnswers;
import com.sixofrods.edtech.entity.QuizQuestions;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public  class   QuizMapper {

    public QuizQuestionDTO toQuestionDTO(QuizQuestions entity) {
        if (entity == null) return null;

        QuizQuestionDTO dto = new QuizQuestionDTO();
        dto.setId(entity.getId());
        dto.setQuestion(entity.getQuestion());
        dto.setAnswers(toAnswerDTOs(entity.getAnswers()));
        return dto;
    }

    public List<QuizQuestionDTO> toQuestionDTOs(List<QuizQuestions> entities) {
        if (entities == null) return null;
        return entities.stream()
            .map(this::toQuestionDTO)
            .collect(Collectors.toList());
    }

    public QuizAnswerDTO toAnswerDTO(QuizAnswers entity) {
        if (entity == null) return null;

        QuizAnswerDTO dto = new QuizAnswerDTO();
        dto.setId(entity.getId());
        dto.setAnswer(entity.getAnswer());
        dto.setCorrect(entity.isCorrect());
        dto.setPoint(entity.getPoint());
        return dto;
    }

    public List<QuizAnswerDTO> toAnswerDTOs(List<QuizAnswers> entities) {
        if (entities == null) return null;
        return entities.stream()
            .map(this::toAnswerDTO)
            .collect(Collectors.toList());
    }

    public QuizQuestions toQuestionEntity(QuizQuestionDTO dto) {
        if (dto == null) return null;

        QuizQuestions entity = new QuizQuestions();
        entity.setQuestion(dto.getQuestion());
        return entity;
    }

    public QuizAnswers toAnswerEntity(QuizAnswerDTO dto) {
        if (dto == null) return null;

        QuizAnswers entity = new QuizAnswers();
        entity.setAnswer(dto.getAnswer());
        entity.setCorrect(dto.isCorrect());
        entity.setPoint(dto.getPoint());
        return entity;
    }
}
