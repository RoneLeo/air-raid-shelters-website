package com.chiy.rfgc.repository;

import com.chiy.rfgc.entity.EquipmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EquipmentRepository extends CrudRepository<EquipmentEntity, Long> {

    EquipmentEntity save(EquipmentEntity entity);

    // 通过id查询
    EquipmentEntity findById(Integer id);

    // 通过id删除
    @Query(value = "delete from equipment where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

    // 通过公司id和产品类型查询
    List<EquipmentEntity> findAllByGsidAndSblxOrderByCjsjDesc(Integer gsid, Integer sblx);

    Page<EquipmentEntity> findAllByGsidAndSblxOrderByCjsjDesc(Integer gsid, Integer sblx, Pageable pageable);


    // 通过设备类型删除
    @Query(value = "delete from equipment where equipment_type = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteBySblx(Integer sblx);

    @Query(value = "select distinct id from equipment where equipment_type = ?1", nativeQuery = true)
    List<Integer> findAllIdBySblx(Integer sblx);

    Page<EquipmentEntity> findAllByGsid(Integer gsid, Pageable pageable);

    List<EquipmentEntity> findAll();

}
