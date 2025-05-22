package com.sixofrods.edtech.repository;

import com.sixofrods.edtech.entity.LanguageGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LanguageGameRP extends JpaRepository<LanguageGame, Long> {
    Optional<LanguageGame> findByLanguageId(Long languageId);
}
