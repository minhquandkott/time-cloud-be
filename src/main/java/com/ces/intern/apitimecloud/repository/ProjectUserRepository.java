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

        List<ProjectUserEntity> getAllByIsDoingAndEmbedId_UserId(Boolean isDoing, Integer userId);

        ProjectUserEntity getByEmbedIdProjectIdAndEmbedIdUserId(Integer projectId, Integer userId);

        int countByIsDoingAndEmbedId_UserId(Boolean isDoing, Integer userId);

        @Modifying
        @Query(value = "update project_user set is_doing = false where project_id = :projectId", nativeQuery = true)
        void deleteProjectById(@Param("projectId") Integer projectId);

        @Query(value = "select project_user.project_id,user_id,is_doing,index,is_show from project_user join project on project_user.project_id = project.project_id\n" +
                "where user_id = :userId and done = false;", nativeQuery = true)
        List<ProjectUserEntity> getAllByUserIdAndNotDone(@Param("userId") Integer userId);


        List<ProjectUserEntity> getAllByEmbedId_ProjectIdAndIsDoing(Integer projectId, boolean isDoing);
}
