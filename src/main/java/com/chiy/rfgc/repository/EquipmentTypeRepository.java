package com.chiy.rfgc.repository;

import com.chiy.rfgc.entity.EquipmenttypeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EquipmentTypeRepository extends CrudRepository<EquipmenttypeEntity, Long> {

    // 通过id查询
    EquipmenttypeEntity findById(Integer id);

    // 查询所以
    List<EquipmenttypeEntity> findAll();



}
