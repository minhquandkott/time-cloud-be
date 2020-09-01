package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    public Optional<UserEntity> findByEmail(String email);

    @Query(value = "select * from public.user where user_id in (select user_company.user_id from user_company where company_id =:companyId)",
            nativeQuery = true)
    public List<UserEntity> getAllByCompanyId(@Param("companyId") Integer companyId);

    @Query(value = "select * from public.user where user_id in (select user_company.user_id from user_company where company_id =:companyId and role =:role)",
            nativeQuery = true)
    public List<UserEntity> getAllByByCompanyIdAAndRole(@Param("companyId")Integer companyId, @Param("role") Integer role);
}
