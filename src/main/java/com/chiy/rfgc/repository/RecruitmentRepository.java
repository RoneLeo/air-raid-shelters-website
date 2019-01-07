package com.chiy.rfgc.repository;

import com.chiy.rfgc.entity.ProjectcaseEntity;
import com.chiy.rfgc.entity.RecruitmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RecruitmentRepository extends CrudRepository<RecruitmentEntity, Long> {

    RecruitmentEntity save(RecruitmentEntity entity);

    // 通过id查询
    RecruitmentEntity findById(Integer id);

    // 通过id删除
    @Query(value = "delete from recruitment where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

    // 通过公司id查询
    Page<RecruitmentEntity> findAllByGsidOrderByCjsjDesc(Integer gsid, Pageable pageable);

}
