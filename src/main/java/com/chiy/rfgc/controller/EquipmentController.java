package com.chiy.rfgc.controller;

import com.chiy.rfgc.common.ApiResult;
import com.chiy.rfgc.entity.EquipmentEntity;
import com.chiy.rfgc.repository.CompanyRepository;
import com.chiy.rfgc.repository.EquipmentRepository;
import com.chiy.rfgc.repository.EquipmentTypeRepository;
import com.chiy.rfgc.repository.UserRepository;
import com.chiy.rfgc.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@Api(description = "产品中心管理")
@RestController
@RequestMapping(value = "/equipment", method = {RequestMethod.GET, RequestMethod.POST})
public class EquipmentController {

    @Resource
    private EquipmentRepository equipmentRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private CompanyRepository companyRepository;
    @Resource
    private EquipmentTypeRepository equipmentTypeRepository;

    @ApiOperation(value = "添加")
    @RequestMapping("/add")
    public ApiResult<Object> add(String uuid, EquipmentEntity entity) {
        // 判断是否登录
        if (StringUtils.isEmpty(uuid) || userRepository.findById(uuid) == null) {
            return ApiResult.FAILURE("未登录");
        }
        // 判断公司id不能为空，是否存在
        if (entity.getGsid() == null || companyRepository.findById(entity.getGsid()) == null) {
            return ApiResult.FAILURE("添加失败，公司id不能为空或该公司id不存在");
        }
        // 判断设备类型
        if (entity.getSblx() == null || equipmentTypeRepository.findById(entity.getSblx()) == null) {
            return ApiResult.FAILURE("添加失败，设备类型不能为空或该设备类型不存在");
        }
        entity.setCjsj(new Date());
        EquipmentEntity entity1 = equipmentRepository.save(entity);
        if (entity1 == null) {
            return ApiResult.FAILURE("添加失败");
        }
        return ApiResult.SUCCESS(entity1);
    }

    @ApiOperation("修改")
    @RequestMapping("/update")
    public ApiResult<Object> update(String uuid, EquipmentEntity entity) {
        // 判断是否登录
        if (StringUtils.isEmpty(uuid) || userRepository.findById(uuid) == null) {
            return ApiResult.FAILURE("未登录");
        }
        // 判断公司id不能为空，是否存在
        if (entity.getGsid() == null || companyRepository.findById(entity.getGsid()) == null) {
            return ApiResult.FAILURE("添加失败，公司id不能为空或该公司id不存在");
        }
        // 判断设备类型
        if (entity.getSblx() == null || equipmentTypeRepository.findById(entity.getSblx()) == null) {
            return ApiResult.FAILURE("修改失败，设备类型不能为空或该设备类型不存在");
        }
        EquipmentEntity entity1 = equipmentRepository.save(entity);
        if (entity1 == null) {
            return ApiResult.FAILURE("修改失败");
        }
        return ApiResult.SUCCESS(entity1);
    }

    @ApiOperation("删除")
    @RequestMapping("/delete")
    public ApiResult<Object> delete(String uuid, Integer id) {
        // 判断是否登录
        if (StringUtils.isEmpty(uuid) || userRepository.findById(uuid) == null) {
            return ApiResult.FAILURE("未登录");
        }
        if (id == null) {
            return ApiResult.FAILURE("id不能为空");
        }
        // 查询是否存在
        if (equipmentRepository.findById(id) == null) {
            return ApiResult.FAILURE("不存在，修改失败");
        }
        int result = equipmentRepository.deleteById(id);
        if (result == 0) {
            return ApiResult.FAILURE("删除失败");
        }
        return ApiResult.SUCCESS("删除成功");
    }

    @ApiOperation("通过公司id查询分页显示")
    @RequestMapping("/findAllByGsidByPage")
    public ApiResult<Object> findAllByGsidByPage(String uuid, int page, int size) {
        // 判断是否登录
        if (StringUtils.isEmpty(uuid) || userRepository.findById(uuid) == null) {
            return ApiResult.FAILURE("未登录");
        }
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<EquipmentEntity> list = equipmentRepository.findAllByGsidOrderByCjsjDesc(userRepository.findById(uuid).getGsid(), pageable);

        return ApiResult.SUCCESS(list);

    }
}
