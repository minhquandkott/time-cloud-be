package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleEntity.Id> {
}
