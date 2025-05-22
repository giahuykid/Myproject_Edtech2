package com.sixofrods.edtech.repository;

import com.sixofrods.edtech.entity.GameColletion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlashcardGameCollection  extends JpaRepository<GameColletion, Long> {
    // Custom query methods can be defined here if needed
}
