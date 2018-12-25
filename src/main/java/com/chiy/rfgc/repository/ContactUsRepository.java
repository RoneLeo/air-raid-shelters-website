package com.chiy.rfgc.repository;

import com.chiy.rfgc.entity.ContactusEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ContactUsRepository extends CrudRepository<ContactusEntity, Long> {

    ContactusEntity save(ContactusEntity entity);

    // 通过id查询
    ContactusEntity findById(Integer id);

    // 通过id删除
    @Query(value = "delete from contactus where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

    // 通过公司id查询
    Page<ContactusEntity> findAllByGsidOrderByCjsjDesc(Integer gsid, Pageable pageable);

}
