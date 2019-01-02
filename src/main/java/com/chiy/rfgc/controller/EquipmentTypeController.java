package com.chiy.rfgc.controller;


import com.chiy.rfgc.common.ApiResult;
import com.chiy.rfgc.entity.EquipmentEntity;
import com.chiy.rfgc.entity.EquipmenttypeEntity;
import com.chiy.rfgc.repository.EquipmentTypeRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(description = "设备类型")
@RestController
@RequestMapping(value = "/equipmentType", method = {RequestMethod.GET, RequestMethod.POST})
public class EquipmentTypeController {

    @Resource
    private EquipmentTypeRepository equipmentTypeRepository;

    @ApiOperation(value = "查询所以设备类型")
    @RequestMapping("/findAll")
    public ApiResult<Object> findAll() {

        List<EquipmenttypeEntity> list = equipmentTypeRepository.findAll();

        return ApiResult.SUCCESS(list);
    }
}
