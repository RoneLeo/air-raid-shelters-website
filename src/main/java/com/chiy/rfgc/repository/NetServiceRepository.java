package com.chiy.rfgc.repository;

import com.chiy.rfgc.entity.NetserviceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface NetServiceRepository extends CrudRepository<NetserviceEntity, Long> {

    NetserviceEntity save(NetserviceEntity entity);

    // 通过id查询
    NetserviceEntity findById(Integer id);

    // 通过id删除
    @Query(value = "delete from netservice where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

    // 通过公司id查询
    Page<NetserviceEntity> findAllByGsidOrderByCjsjDesc(Integer gsid, Pageable pageable);
}
