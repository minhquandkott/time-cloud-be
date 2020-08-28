package com.ces.intern.apitimecloud.repository;

import com.ces.intern.apitimecloud.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository  extends JpaRepository<CompanyEntity, Integer> {

}
