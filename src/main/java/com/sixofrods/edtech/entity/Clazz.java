package com.sixofrods.edtech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@Table(name = "classes")
@NoArgsConstructor
@AllArgsConstructor
public class Clazz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String className;
    private String maxCapacity;
    private String semester;
    private String isActive;
    private LocalDateTime createdAt;
    private String createdBy;
    private String password;
    @OneToMany(mappedBy = "classes")
    private List<EnrolledStudent> enrollments;
    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;
    @OneToMany(mappedBy = "clazz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassMaterial> materials = new ArrayList<>();
    @OneToMany(mappedBy = "clazz")
    private List<ClassQuiz> classQuiz;


}
