package com.sixofrods.edtech.repository;

import com.sixofrods.edtech.entity.Mock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashcardGameRP extends JpaRepository <Mock, Long> {

}
