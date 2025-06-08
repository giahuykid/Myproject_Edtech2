package com.sixofrods.edtech.repository;

import com.sixofrods.edtech.entity.FlashcardCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashcardCollectionRP extends JpaRepository<FlashcardCollection, Long> {
    // This interface can be extended with custom query methods if needed
}
