package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleEntity.EmbedId> {
    List<UserRoleEntity> findAllByEmbedIdCompanyId(Integer companyId);

    List<UserRoleEntity> findAllByEmbedIdCompanyIdAndRoleId(Integer companyId, Integer roleId);

    List<UserRoleEntity> findAllByEmbedIdCompanyIdAndUserId(Integer companyId, Integer userId);

}
