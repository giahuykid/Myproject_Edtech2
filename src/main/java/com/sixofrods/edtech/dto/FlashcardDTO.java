package com.sixofrods.edtech.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlashcardDTO {
    private Long id;
    private String word;
    private String meaning;
    private Long collectionId;
} 