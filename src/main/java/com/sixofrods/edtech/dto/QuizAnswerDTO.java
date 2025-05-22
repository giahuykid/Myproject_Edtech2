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
public class QuizAnswerDTO {
    private Long id;
    private String answer;
    private boolean correct;
    private int point;
}
