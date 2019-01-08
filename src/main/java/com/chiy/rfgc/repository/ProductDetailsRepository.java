package com.chiy.rfgc.repository;

import com.chiy.rfgc.entity.ProductdetailsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductDetailsRepository extends CrudRepository<ProductdetailsEntity, Long> {


    ProductdetailsEntity save(ProductdetailsEntity entity);

    // 通过id查询
    ProductdetailsEntity findById(Integer id);

    // 通过id删除
    @Query(value = "delete from productdetails where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

    // 通过产品id查询
    Page<ProductdetailsEntity> findAllByCpidOrderById(Integer cpid, Pageable pageable);

    List<ProductdetailsEntity> findAllByCpid(Integer cpid);

    // 通过产品id删除
    @Query(value = "delete from productdetails where product_id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    boolean deleteByCpId(Integer cpid);

}
