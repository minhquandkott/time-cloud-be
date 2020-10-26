package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.ProjectUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface ProjectUserRepository extends JpaRepository<ProjectUserEntity, ProjectUserEntity.EmbedId> {

        List<ProjectUserEntity> getAllByEmbedIdProjectId(Integer projectId);

        List<ProjectUserEntity> getAllByEmbedIdUserId(@Param("userId") Integer userId);

        ProjectUserEntity getByEmbedIdProjectIdAndEmbedIdUserId(Integer projectId, Integer userId);

        @Modifying
        @Query(value = "update project_user set is_doing = false where project_id = :projectId", nativeQuery = true)
        void deleteProjectById(@Param("projectId") Integer projectId);

//        @Query(value = "select * from project_user where project_user.user_id =:userId", nativeQuery = true)
//        List<ProjectUserEntity> getAllByEmbedUserId(@Param("userId") Integer userId);
}
