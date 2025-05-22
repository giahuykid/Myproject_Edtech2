package com.sixofrods.edtech.repository;

import com.sixofrods.edtech.entity.QuizAnswers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  QuizAnswersRP extends JpaRepository<QuizAnswers, Long> {


}
