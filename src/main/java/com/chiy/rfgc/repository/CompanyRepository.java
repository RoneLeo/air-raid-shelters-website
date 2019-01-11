package com.chiy.rfgc.repository;

import com.chiy.rfgc.entity.CompanyEntity;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<CompanyEntity, Long> {

    // 通过id查询
    CompanyEntity findById(Integer id);

    CompanyEntity save(CompanyEntity entity);


}
