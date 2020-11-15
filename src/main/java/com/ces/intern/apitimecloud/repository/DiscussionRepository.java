package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.DiscussionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface DiscussionRepository extends JpaRepository<DiscussionEntity, Integer> {

    List<DiscussionEntity> getAllByProjectId(Integer projectId);

    List<DiscussionEntity> getAllByProjectIdAndType(Integer projectId, Integer type);



    @Query(value = "select * from discussion where  project_id in (select project_id from project_user where project_user.user_id =:userId) order by :orderBy ASC limit :limit offset :offset",nativeQuery = true)
    List<DiscussionEntity> getAllByUserIdInProject(
                                    @Param(value = "userId")Integer userId,
                                    @Param(value = "orderBy") String orderBy,
                                    @Param(value = "limit")Integer limit,
                                    @Param(value = "offset") Integer offset);
}
