package com.sixofrods.edtech.repository;

import com.sixofrods.edtech.entity.FlashcardCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashCardCollectionRP extends JpaRepository<FlashcardCollection, Long> {

    // This interface extends FlashcardRP, which means it inherits all methods from FlashcardRP.
    // You can add additional methods specific to FlashCardCollection if needed.
}
