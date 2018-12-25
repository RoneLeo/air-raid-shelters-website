package com.chiy.rfgc.repository;

import com.chiy.rfgc.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity save(UserEntity entity);

    // 通过账号查询
    UserEntity findByZh(String zh);

    // 通过账号和密码查询
    UserEntity findByZhAndMm(String zh, String mm);

    // 通过id查询
    UserEntity findById(String id);

    // 通过id删除
    @Query(value = "delete from user where id=?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(String id);

    List<UserEntity> findAllByGsidOrderByCjsjDesc(Integer gsid);

}
