package com.sixofrods.edtech.repository;

import com.sixofrods.edtech.entity.Mock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MockRP extends JpaRepository <Mock, Long> {
    List<Mock> findByUserId(Long userId);
    List<Mock> findByLanguageId(Long languageId);
    List<Mock> findByUserIdAndLanguageId(Long userId, Long languageId);

}
