package com.sixofrods.edtech.controller;

import com.sixofrods.edtech.dto.FlashcardDTO;
import com.sixofrods.edtech.dto.FlashcardCollectionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/flashcard")
public interface FlashcardController {
    @PostMapping("/createflashcard")
    public ResponseEntity<FlashcardDTO> createFlashcard(
            @RequestParam String word,
            @RequestParam String meaning,
            @RequestParam Long collectionId
    );
    @PostMapping("/createflashcardcollection")
    public ResponseEntity<FlashcardCollectionDTO> createFlashcardCollection(
            @RequestParam String name,
            @RequestParam Long userId
    );

    @DeleteMapping("/deleteflashcard/{id}")
    public ResponseEntity<?> deleteFlashcard(@PathVariable Long id);

    @DeleteMapping("/deletecollection/{id}")
    public ResponseEntity<?> deleteFlashcardCollection(@PathVariable Long id);
    @PatchMapping("/updateflashcard/{id}")
    public ResponseEntity<FlashcardDTO> updateFlashcard(
            @PathVariable Long id,
            @RequestParam String word,
            @RequestParam String meaning
    );
    @GetMapping("/collections")
    public ResponseEntity<List<FlashcardCollectionDTO>> getAllFlashcardCollections();
    @GetMapping("/{id}")
    public ResponseEntity<FlashcardDTO> getFlashcardById(@PathVariable Long id);
    }
