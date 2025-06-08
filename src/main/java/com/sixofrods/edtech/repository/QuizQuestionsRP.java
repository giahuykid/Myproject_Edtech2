package com.sixofrods.edtech.repository;

import com.sixofrods.edtech.entity.QuizQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface QuizQuestionsRP extends JpaRepository<QuizQuestions, Long> {

}
