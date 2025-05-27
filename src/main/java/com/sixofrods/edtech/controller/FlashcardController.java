package com.sixofrods.edtech.controller;

import com.sixofrods.edtech.entity.Flashcard;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/flashcard")
public interface FlashcardController {
    @PostMapping("/createflashcard")
    public ResponseEntity<Flashcard> createFlashcard(
            @RequestParam String word,
            @RequestParam String meaning,
            @RequestParam Long collectionId
    );
    @PostMapping("/createflashcardcollection")
    public ResponseEntity<?> createFlashcardCollection(
            @RequestParam String name,
            @RequestParam Long userId
    );

    @DeleteMapping("/deleteflashcard/{id}")
    public ResponseEntity<?> deleteFlashcard(@PathVariable Long id);

    @DeleteMapping("/deletecollection/{id}")
    public ResponseEntity<?> deleteFlashcardCollection(@PathVariable Long id);
    @PatchMapping("/updateflashcard/{id}")
    public ResponseEntity<Flashcard> updateFlashcard(
            @PathVariable Long id,
            @RequestParam String word,
            @RequestParam String meaning
    );


}
