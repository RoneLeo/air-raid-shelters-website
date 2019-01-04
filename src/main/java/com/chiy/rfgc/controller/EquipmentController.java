package com.chiy.rfgc.controller;

import com.chiy.rfgc.common.ApiResult;
import com.chiy.rfgc.entity.EquipmentEntity;
import com.chiy.rfgc.repository.EquipmentRepository;
import com.chiy.rfgc.repository.EquipmentTypeRepository;
import com.chiy.rfgc.repository.UserRepository;
import com.chiy.rfgc.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
    private EquipmentTypeRepository equipmentTypeRepository;
    @Resource
    private UserController userController;

    private static final String EQUIPMENT_PHOTO_PATH = "/equipment/";

    @ApiOperation(value = "添加")
    @RequestMapping("/add")
    public ApiResult<Object> add(EquipmentEntity entity, HttpServletRequest request, MultipartFile file) throws IOException {
        String uuid = userController.getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResult.UNKNOWN();
        }
        // 判断设备类型
        if (entity.getSblx() == null || equipmentTypeRepository.findById(entity.getSblx()) == null) {
            return ApiResult.FAILURE("添加失败，设备类型不能为空或该设备类型不存在");
        }
        // 添加图片
        if (file != null) {
            FileUtils.addPhoto(request, EQUIPMENT_PHOTO_PATH, file);
            entity.setCptp(EQUIPMENT_PHOTO_PATH + file.getOriginalFilename());
        }
        entity.setGsid(userRepository.findByUuid(uuid).getGsid());
        entity.setCjsj(new Date());
        EquipmentEntity entity1 = equipmentRepository.save(entity);
        if (entity1 == null) {
            return ApiResult.FAILURE("添加失败");
        }
        return ApiResult.SUCCESS(entity1);
    }

    @ApiOperation("修改")
    @RequestMapping("/update")
    public ApiResult<Object> update(HttpServletRequest request, EquipmentEntity entity, MultipartFile file) throws IOException {
        String uuid = userController.getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResult.UNKNOWN();
        }
        // 判断设备类型
        if (entity.getSblx() == null || equipmentTypeRepository.findById(entity.getSblx()) == null) {
            return ApiResult.FAILURE("修改失败，设备类型不能为空或该设备类型不存在");
        }
        // 添加图片
        if (file != null) {
            FileUtils.addPhoto(request, EQUIPMENT_PHOTO_PATH, file);
            entity.setCptp(EQUIPMENT_PHOTO_PATH + file.getOriginalFilename());
        }
        EquipmentEntity entity1 = equipmentRepository.save(entity);
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
    public ApiResult<Object> findAllByGsidByPage(HttpServletRequest request, int page, int size) {
        String uuid = userController.getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResult.UNKNOWN();
        }
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<EquipmentEntity> list = equipmentRepository.findAllByGsidOrderByCjsjDesc(userRepository.findByUuid(uuid).getGsid(), pageable);

        return ApiResult.SUCCESS(list);

    }

    @ApiOperation("前端显示公司信息")
    @RequestMapping("/findAllByGsid")
    public ApiResult<Object> findAllByGsid(Integer gsid, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<EquipmentEntity> list = equipmentRepository.findAllByGsidOrderByCjsjDesc(gsid, pageable);

        return ApiResult.SUCCESS(list);
    }

}
