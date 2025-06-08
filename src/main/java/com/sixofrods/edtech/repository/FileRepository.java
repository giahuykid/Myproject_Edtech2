package com.sixofrods.edtech.repository;

import com.sixofrods.edtech.entity.FileEntity;
import com.sixofrods.edtech.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    Optional<FileEntity> findByFileName(String fileName);
    boolean existsByFileName(String fileName);
    List<FileEntity> findByLanguage(Language language);
    List<FileEntity> findByLanguage_LanguageName(String languageName);
}
