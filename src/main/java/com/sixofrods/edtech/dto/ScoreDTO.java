package com.sixofrods.edtech.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreDTO {
    private Long id;
    private int score;
    private LocalDateTime submittedAt;
    private Long userId;
    private Long mockId;
} 