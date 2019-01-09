package com.chiy.rfgc.controller;


import com.chiy.rfgc.common.ApiResult;
import com.chiy.rfgc.entity.ContactusEntity;
import com.chiy.rfgc.entity.EquipmenttypeEntity;
import com.chiy.rfgc.repository.EquipmentTypeRepository;
import com.chiy.rfgc.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Api(description = "设备类型")
@RestController
@RequestMapping(value = "/equipmentType", method = {RequestMethod.GET, RequestMethod.POST})
public class EquipmentTypeController {

    @Resource
    private EquipmentTypeRepository equipmentTypeRepository;
    @Resource
    private UserController userController;
    @Resource
    private UserRepository userRepository;

    @ApiOperation(value = "添加")
    @RequestMapping("/add")
    public ApiResult<Object> add(EquipmenttypeEntity entity, HttpServletRequest request) {
        String uuid = userController.getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResult.UNKNOWN();
        }
        //
        entity.setGsid(userRepository.findByUuid(uuid).getGsid());
        EquipmenttypeEntity entity1 = equipmentTypeRepository.save(entity);
        if (entity1 == null) {
            return ApiResult.FAILURE("添加失败");
        }
        return ApiResult.SUCCESS(entity1);
    }

    @ApiOperation("修改")
    @RequestMapping("/update")
    public ApiResult<Object> update(HttpServletRequest request, EquipmenttypeEntity entity) {
        String uuid = userController.getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResult.UNKNOWN();
        }
        // 判断是否存在
        if (equipmentTypeRepository.findById(entity.getId()) == null) {
            return ApiResult.FAILURE("不存在，修改失败");
        }
        EquipmenttypeEntity entity1 = equipmentTypeRepository.save(entity);
        if (entity1 == null) {
            return ApiResult.FAILURE("修改失败");
        }
        return ApiResult.SUCCESS(entity1);
    }

    @ApiOperation("删除")
    @RequestMapping("/delete")
    public ApiResult<Object> delete(HttpServletRequest request, Integer id) {
        String uuid = userController.getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResult.UNKNOWN();
        }
        if (id == null) {
            return ApiResult.FAILURE("id不能为空");
        }
        // 查询是否存在
        if (equipmentTypeRepository.findById(id) == null) {
            return ApiResult.FAILURE("不存在，修改失败");
        }
        int result = equipmentTypeRepository.deleteById(id);
        if (result == 0) {
            return ApiResult.FAILURE("删除失败");
        }
        // 删除相关

        return ApiResult.SUCCESS("删除成功");
    }

    @ApiOperation("后台通过公司id查询所有")
    @RequestMapping("/findAllByGsid")
    public ApiResult<Object> findAllByGsidByPage(HttpServletRequest request) {
        String uuid = userController.getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResult.UNKNOWN();
        }
        List<EquipmenttypeEntity> list = equipmentTypeRepository.findAllByGsid(userRepository.findByUuid(uuid).getGsid());
        return ApiResult.SUCCESS(list);
    }


    @ApiOperation("通过id查询")
    @RequestMapping("/findById")
    public ApiResult<Object> findById(Integer id) {
        if (id == null) {
            return ApiResult.FAILURE("id不能为空");
        }
        EquipmenttypeEntity entity = equipmentTypeRepository.findById(id);
        return ApiResult.SUCCESS(entity);
    }

    @ApiOperation("前端通过公司id查询所有")
    @RequestMapping("/findByGsid")
    public ApiResult<Object> findByGsid(Integer gsid) {
        if (gsid == null) {
            return ApiResult.FAILURE("公司id不能为空");
        }
        List<EquipmenttypeEntity> list = equipmentTypeRepository.findAllByGsid(gsid);
        return ApiResult.SUCCESS(list);
    }


}
