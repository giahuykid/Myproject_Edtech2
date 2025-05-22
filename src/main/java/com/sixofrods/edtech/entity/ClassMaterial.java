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
@Table(name = "class_material")
@NoArgsConstructor
@AllArgsConstructor
public class ClassMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime uploadedTime;
    private String summary;
    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private Clazz clazz;


}
