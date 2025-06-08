package com.sixofrods.edtech.repository;

import com.sixofrods.edtech.entity.GameColletion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameColletionRP extends JpaRepository<GameColletion, Long> {

}
