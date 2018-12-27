package com.chiy.rfgc.repository;

import com.chiy.rfgc.entity.EquipmenttypeEntity;
import org.springframework.data.repository.CrudRepository;

public interface EquipmentTypeRepository extends CrudRepository<EquipmenttypeEntity, Long> {

    // 通过id查询
    EquipmenttypeEntity findById(Integer id);
}
