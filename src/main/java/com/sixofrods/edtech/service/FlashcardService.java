package com.sixofrods.edtech.service;

import com.sixofrods.edtech.entity.Flashcard;
import com.sixofrods.edtech.entity.FlashcardCollection;

import java.util.List;

public interface FlashcardService {
    public FlashcardCollection createCollection(String name, Long userId);
    public Flashcard createFlashcard( String word, String meaning, Long collectionId);
    public Flashcard getFlashcardById(Long id);
    public Flashcard updateFlashcard(Long id, String word, String meaning);
    public void deleteFlashcard(Long id);
    public void delteFlashcardCollection(Long id);
    public List<FlashcardCollection> getAllFlashcardCollections();

    }
