package com.chiy.rfgc.repository;

import com.chiy.rfgc.entity.EquipmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface EquipmentRepository extends CrudRepository<EquipmentEntity, Long> {

    EquipmentEntity save(EquipmentEntity entity);

    // 通过id查询
    EquipmentEntity findById(Integer id);

    // 通过id删除
    @Query(value = "delete from equipment where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

    // 通过公司id查询
    Page<EquipmentEntity> findAllByGsidOrderByCjsjDesc(Integer gsid, Pageable pageable);
}
