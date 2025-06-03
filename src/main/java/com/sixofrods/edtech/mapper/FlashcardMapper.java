package com.sixofrods.edtech.mapper;

import com.sixofrods.edtech.dto.FlashcardDTO;
import com.sixofrods.edtech.dto.FlashcardCollectionDTO;
import com.sixofrods.edtech.entity.Flashcard;
import com.sixofrods.edtech.entity.FlashcardCollection;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FlashcardMapper {

    public FlashcardDTO toDTO(Flashcard flashcard) {
        return FlashcardDTO.builder()
                .id(flashcard.getId())
                .word(flashcard.getWord())
                .meaning(flashcard.getMeaning())
                .collectionId(flashcard.getCollection().getId())
                .build();
    }

    public FlashcardCollectionDTO toDTO(FlashcardCollection collection) {
        List<FlashcardDTO> flashcardDTOs = collection.getFlashcards().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        return FlashcardCollectionDTO.builder()
                .id(collection.getId())
                .name(collection.getName())
                .numberOfFlashcards(collection.getNumberOfFlashcards())
                .userId(collection.getUser().getId())
                .flashcards(flashcardDTOs)
                .build();
    }

    public Flashcard toEntity(FlashcardDTO dto) {
        return Flashcard.builder()
                .id(dto.getId())
                .word(dto.getWord())
                .meaning(dto.getMeaning())
                .build();
    }

    public FlashcardCollection toEntity(FlashcardCollectionDTO dto) {
        return FlashcardCollection.builder()
                .id(dto.getId())
                .name(dto.getName())
                .numberOfFlashcards(dto.getNumberOfFlashcards())
                .build();
    }
} 