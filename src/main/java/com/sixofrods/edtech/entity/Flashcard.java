package com.sixofrods.edtech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@Table(name = "flashcard")
@NoArgsConstructor
@AllArgsConstructor
public class Flashcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String word;
    private String meaning;
    @ManyToOne
    @JoinColumn(name = "collection_id", nullable = false)
    private FlashcardCollection collection;

}
