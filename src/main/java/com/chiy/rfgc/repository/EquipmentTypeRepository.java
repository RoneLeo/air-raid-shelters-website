package com.chiy.rfgc.repository;

import com.chiy.rfgc.entity.EquipmenttypeEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EquipmentTypeRepository extends CrudRepository<EquipmenttypeEntity, Long> {

    // 通过id查询
    EquipmenttypeEntity findById(Integer id);

    // 查询所有
    List<EquipmenttypeEntity> findAll();

    // 通过公司id查询所以
    List<EquipmenttypeEntity> findAllByGsid(Integer gsid);

    // 通过id删除
    @Query(value = "delete from equipmenttype where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);




}
