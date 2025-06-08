package com.sixofrods.edtech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(name = "word_collection")
@NoArgsConstructor
@AllArgsConstructor
public class WordCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String word;
    private LocalDateTime modifiedAt;
    private String modifiedBy;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User user;

}
