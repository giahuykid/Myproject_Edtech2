package com.sixofrods.edtech.repository;

import com.sixofrods.edtech.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface LanguageRP extends JpaRepository<Language, Long> {
    Optional<Language> findByLanguageName(String languageName);

}
