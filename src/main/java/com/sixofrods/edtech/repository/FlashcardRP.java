package com.sixofrods.edtech.repository;

import com.sixofrods.edtech.entity.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FlashcardRP extends JpaRepository<Flashcard, Long> {

}
