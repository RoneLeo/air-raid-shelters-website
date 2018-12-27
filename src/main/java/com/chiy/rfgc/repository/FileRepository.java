package com.chiy.rfgc.repository;

import com.chiy.rfgc.entity.ContactusEntity;
import com.chiy.rfgc.entity.EquipmentEntity;
import com.chiy.rfgc.entity.FileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface FileRepository extends CrudRepository<FileEntity, Long> {


    FileEntity save(FileEntity entity);

    // 通过id查询
    FileEntity findById(Integer id);

    // 通过id删除
    @Query(value = "delete from file where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

    // 通过公司id和文件类型查询
    Page<FileEntity> findAllByGsidAndWjlxOrderByCjsjDesc(Integer gsid, Integer wjlx, Pageable pageable);
}
