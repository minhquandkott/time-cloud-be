package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.DiscussionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface DiscussionRepository extends JpaRepository<DiscussionEntity, Integer> {

    Page<DiscussionEntity> getAllByProjectId(Integer projectId, Pageable pageable);

    Page<DiscussionEntity> getAllByProjectIdAndType(Integer projectId, Integer type, Pageable pageable);

    @Query(value = "select * from discussion where  project_id in (select project_id from project_user where project_user.user_id =:userId) order by create_at DESC limit :limit offset :offset",nativeQuery = true)
    List<DiscussionEntity> getAllByUserIdInProject(
                                    @Param(value = "userId")Integer userId,
                                    @Param(value = "limit")Integer limit,
                                    @Param(value = "offset") Integer offset);

    @Query(value = "select * from discussion where type =:type and project_id in (select project_id from project_user where project_user.user_id =:userId) order by create_at DESC limit :limit offset :offset",nativeQuery = true)
    List<DiscussionEntity> getAllByUserIdAndTypeInProject(
            @Param(value = "userId")Integer userId,
            @Param(value = "limit")Integer limit,
            @Param(value = "offset") Integer offset,
            @Param(value = "type")Integer type);



}
