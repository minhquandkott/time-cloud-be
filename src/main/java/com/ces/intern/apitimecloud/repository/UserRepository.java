package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
