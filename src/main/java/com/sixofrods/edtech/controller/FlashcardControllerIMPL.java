package com.sixofrods.edtech.controller;

import com.sixofrods.edtech.dto.FlashcardDTO;
import com.sixofrods.edtech.dto.FlashcardCollectionDTO;
import com.sixofrods.edtech.entity.Flashcard;
import com.sixofrods.edtech.entity.FlashcardCollection;
import com.sixofrods.edtech.mapper.FlashcardMapper;
import com.sixofrods.edtech.service.FlashcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FlashcardControllerIMPL implements FlashcardController {
    @Autowired
    private FlashcardService flashcardService;

    @Autowired
    private FlashcardMapper flashcardMapper;

    @Override
    public ResponseEntity<FlashcardDTO> createFlashcard(String word, String meaning, Long collectionId) {
        Flashcard flashcard = flashcardService.createFlashcard(word, meaning, collectionId);
        return ResponseEntity.ok(flashcardMapper.toDTO(flashcard));
    }

    @Override
    public ResponseEntity<FlashcardCollectionDTO> createFlashcardCollection(String name, Long userId) {
        FlashcardCollection collection = flashcardService.createCollection(name, userId);
        return ResponseEntity.ok(flashcardMapper.toDTO(collection));
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
    public ResponseEntity<FlashcardDTO> updateFlashcard(Long id, String word, String meaning) {
        try {
            Flashcard updatedFlashcard = flashcardService.updateFlashcard(id, word, meaning);
            return ResponseEntity.ok(flashcardMapper.toDTO(updatedFlashcard));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<List<FlashcardCollectionDTO>> getAllFlashcardCollections() {
        List<FlashcardCollection> collections = flashcardService.getAllFlashcardCollections();
        if (collections.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<FlashcardCollectionDTO> collectionDTOs = collections.stream()
                .map(flashcardMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(collectionDTOs);
    }

    @Override
    public ResponseEntity<FlashcardDTO> getFlashcardById(Long id) {
        try {
            Flashcard flashcard = flashcardService.getFlashcardById(id);
            return ResponseEntity.ok(flashcardMapper.toDTO(flashcard));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
