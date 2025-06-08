package com.sixofrods.edtech.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashcardCollectionDTO {
    private Long id;
    private String name;
    private long numberOfFlashcards;
    private Long userId;
    private List<FlashcardDTO> flashcards;
} 