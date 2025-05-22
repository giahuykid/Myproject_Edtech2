package com.sixofrods.edtech.repository;

import com.sixofrods.edtech.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRP extends JpaRepository<User, Long> {

}
