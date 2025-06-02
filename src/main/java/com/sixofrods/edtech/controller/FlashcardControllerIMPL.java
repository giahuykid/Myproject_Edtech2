package com.sixofrods.edtech.controller;

import com.sixofrods.edtech.entity.Flashcard;
import com.sixofrods.edtech.entity.FlashcardCollection;
import com.sixofrods.edtech.service.FlashcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FlashcardControllerIMPL implements FlashcardController {
    @Autowired
    private  FlashcardService flashcardService;



    @Override
    public ResponseEntity<Flashcard> createFlashcard(String word, String meaning, Long collectionId) {
        Flashcard flashcard = flashcardService.createFlashcard(word, meaning, collectionId);
        return ResponseEntity.ok(flashcard);
    }

    @Override
    public ResponseEntity<?> createFlashcardCollection(String name, Long userId) {
        FlashcardCollection collection = flashcardService.createCollection(name, userId);
        return ResponseEntity.ok(collection);
    }

    @Override
    public ResponseEntity<?> deleteFlashcard(Long id) {
        try {
            flashcardService.deleteFlashcard(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<?> deleteFlashcardCollection(Long id) {
        try {
            flashcardService.delteFlashcardCollection(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Flashcard> updateFlashcard(Long id, String word, String meaning) {
        try {
            Flashcard updatedFlashcard = flashcardService.updateFlashcard(id, word, meaning);
            return ResponseEntity.ok(updatedFlashcard);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<List<FlashcardCollection>> getAllFlashcardCollections() {
        List<FlashcardCollection> collections = flashcardService.getAllFlashcardCollections();
        if (collections.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(collections);
    }

    @Override
    public ResponseEntity<Flashcard> getFlashcardById(Long id) {
        try {
            Flashcard flashcard = flashcardService.getFlashcardById(id);
            return ResponseEntity.ok(flashcard);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
