package com.chiy.rfgc.repository;

import com.chiy.rfgc.entity.NewsEntity;
import com.chiy.rfgc.entity.ProjectcaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface NewsRepository extends CrudRepository<NewsEntity, Long> {

    NewsEntity save(NewsEntity entity);

    // 通过id查询
    NewsEntity findById(Integer id);

    // 通过id删除
    @Query(value = "delete from news where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

    // 通过公司id查询
    Page<NewsEntity> findAllByGsidAndXwlxOrderByCjsjDesc(Integer gsid, Integer xwlx, Pageable pageable);
}
