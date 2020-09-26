package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleEntity.EmbedId> {
    public List<UserRoleEntity> findAllByEmbedIdCompanyId(Integer companyId);
}
