package com.sixofrods.edtech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@Table(name = "language")
@NoArgsConstructor
@AllArgsConstructor
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String languageName;
    @OneToMany(mappedBy = "language")
    private List<Clazz> Classes;
    @ManyToMany(mappedBy = "languages")
    private List<User> users;
    @OneToMany(mappedBy = "language", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LanguageGame> languageGames;
    @OneToMany(mappedBy = "language")
    private List<ClassQuiz> classQuiz;
    @OneToMany(mappedBy = "language")
    private List<LanguageResource> resources = new ArrayList<>();
    @OneToMany(mappedBy = "language")
    private List<Mock> mocks = new ArrayList<>();
    @ManyToMany(mappedBy = "languages")
    private Set<Scholarship> scholarships = new HashSet<>();
    @OneToMany(mappedBy = "language", cascade = CascadeType.PERSIST)
    private List<FileEntity> files = new ArrayList<>();

}
