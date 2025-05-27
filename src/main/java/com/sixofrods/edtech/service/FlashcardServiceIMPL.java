package com.sixofrods.edtech.service;

import com.sixofrods.edtech.entity.Flashcard;
import com.sixofrods.edtech.entity.FlashcardCollection;
import com.sixofrods.edtech.entity.User;
import com.sixofrods.edtech.repository.FlashcardCollectionRP;
import com.sixofrods.edtech.repository.FlashcardRP;
import com.sixofrods.edtech.repository.UserRP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlashcardServiceIMPL implements FlashcardService {

  @Autowired
    private FlashcardRP flashcardRepository;
  @Autowired
    private FlashcardCollectionRP flashcardCollectionRPRepository;
  @Autowired
  private UserRP userRepository;

    @Override
    public FlashcardCollection createCollection(String name, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        FlashcardCollection collection = FlashcardCollection.builder()
                .name(name)
                .user(user)
                .flashcards(new ArrayList<>())
                .build();

        return flashcardCollectionRPRepository.save(collection);

}

    @Override
    public Flashcard createFlashcard(String word, String meaning, Long collectionId) {
// Verify collection exists
        FlashcardCollection collection = flashcardCollectionRPRepository.findById(collectionId)
                .orElseThrow(() -> new RuntimeException("Collection not found"));

        // Create flashcard
        Flashcard flashcard = Flashcard.builder()
                .word(word)
                .meaning(meaning)
                .collection(collection)
                .build();

        // Increment numberOfFlashcards
        collection.setNumberOfFlashcards(collection.getNumberOfFlashcards() + 1);
        flashcardCollectionRPRepository.save(collection);

        // Save and return flashcard
        return flashcardRepository.save(flashcard);
    }

    @Override
    public Flashcard getFlashcardById(Long id) {
        return flashcardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flashcard not found"));
    }

    @Override
    public Flashcard updateFlashcard(Long id, String word, String meaning) {
        Flashcard flashcard = flashcardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flashcard not found"));

        flashcard.setWord(word);
        flashcard.setMeaning(meaning);

        return flashcardRepository.save(flashcard);
    }

    @Override
    public void deleteFlashcard(Long id) {
        Flashcard flashcard = flashcardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flashcard not found"));

        // Decrement numberOfFlashcards in collection
        FlashcardCollection collection = flashcard.getCollection();
        collection.setNumberOfFlashcards(collection.getNumberOfFlashcards() - 1);
        flashcardCollectionRPRepository.save(collection);

        flashcardRepository.delete(flashcard);
    }

    @Override
    public void delteFlashcardCollection(Long id) {
        FlashcardCollection collection = flashcardCollectionRPRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Collection not found"));
        flashcardCollectionRPRepository.delete(collection);
    }

    @Override
    public List<FlashcardCollection> getAllFlashcardCollections() {
        return flashcardCollectionRPRepository.findAll();
    }

}




