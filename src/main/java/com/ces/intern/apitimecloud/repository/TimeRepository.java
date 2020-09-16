package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.TimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TimeRepository extends JpaRepository<TimeEntity, Integer> {

    public List<TimeEntity> getAllByUserId(Integer userId);

}
