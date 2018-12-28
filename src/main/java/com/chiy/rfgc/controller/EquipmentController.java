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
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    @Resource
    private UserController userController;

    private static final String EQUIPMENT_PHOTO_PATH = "/equipment/";

    @ApiOperation(value = "添加")
    @RequestMapping("/add")
    public ApiResult<Object> add(EquipmentEntity entity,
                                 @ApiParam(value = "产品特点图片") MultipartFile cptdtp1,
                                 @ApiParam(value = "适用范围图片") MultipartFile syfwtp1,
                                 @ApiParam(value = "主要技术参数图片") MultipartFile zyjscstp1,
                                 @ApiParam(value = "单位图片") MultipartFile dwtp1,
                                 @ApiParam(value = "预留备用穿墙管间距尺寸表图片") MultipartFile ylbtp1,
                                 @ApiParam(value = "说明图片") MultipartFile smtp1,
                                 @ApiParam(value = "安装与使用图片") MultipartFile azysytp1,
                                 @ApiParam(value = "性能图片") MultipartFile xntp1,
                                 @ApiParam(value = "分类图片") MultipartFile fltp1,
                                 HttpServletRequest request) {
        // 判断是否登录
        if (userController.judgeNotLogin(request)) {
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
        if (cptdtp1 != null) {
            entity.setCptdtp(EQUIPMENT_PHOTO_PATH + cptdtp1.getOriginalFilename());
        }
        if (syfwtp1 != null) {
            entity.setSyfwtp(EQUIPMENT_PHOTO_PATH + syfwtp1.getOriginalFilename());
        }
        if (zyjscstp1 != null) {
            entity.setZyjscstp(EQUIPMENT_PHOTO_PATH + zyjscstp1.getOriginalFilename());
        }
        if (dwtp1 != null) {
            entity.setDwtp(EQUIPMENT_PHOTO_PATH + dwtp1.getOriginalFilename());
        }
        if (ylbtp1 != null) {
            entity.setYlbtp(EQUIPMENT_PHOTO_PATH + ylbtp1.getOriginalFilename());
        }
        if (smtp1 != null) {
            entity.setSmtp(EQUIPMENT_PHOTO_PATH + smtp1.getOriginalFilename());
        }
        if (azysytp1 != null) {
            entity.setAzysytp(EQUIPMENT_PHOTO_PATH + azysytp1.getOriginalFilename());
        }
        if (xntp1 != null) {
            entity.setXntp(EQUIPMENT_PHOTO_PATH + xntp1.getOriginalFilename());
        }
        if (fltp1 != null) {
            entity.setFltp(EQUIPMENT_PHOTO_PATH + fltp1.getOriginalFilename());
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
    public ApiResult<Object> update(HttpServletRequest request, EquipmentEntity entity,
                                    @ApiParam(value = "产品特点图片") MultipartFile cptdtp1,
                                    @ApiParam(value = "适用范围图片") MultipartFile syfwtp1,
                                    @ApiParam(value = "主要技术参数图片") MultipartFile zyjscstp1,
                                    @ApiParam(value = "单位图片") MultipartFile dwtp1,
                                    @ApiParam(value = "预留备用穿墙管间距尺寸表图片") MultipartFile ylbtp1,
                                    @ApiParam(value = "说明图片") MultipartFile smtp1,
                                    @ApiParam(value = "安装与使用图片") MultipartFile azysytp1,
                                    @ApiParam(value = "性能图片") MultipartFile xntp1,
                                    @ApiParam(value = "分类图片") MultipartFile fltp1) {
        // 判断是否登录
        if (userController.judgeNotLogin(request)) {
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
        if (cptdtp1 != null) {
            entity.setCptdtp(EQUIPMENT_PHOTO_PATH + cptdtp1.getOriginalFilename());
        }
        if (syfwtp1 != null) {
            entity.setSyfwtp(EQUIPMENT_PHOTO_PATH + syfwtp1.getOriginalFilename());
        }
        if (zyjscstp1 != null) {
            entity.setZyjscstp(EQUIPMENT_PHOTO_PATH + zyjscstp1.getOriginalFilename());
        }
        if (dwtp1 != null) {
            entity.setDwtp(EQUIPMENT_PHOTO_PATH + dwtp1.getOriginalFilename());
        }
        if (ylbtp1 != null) {
            entity.setYlbtp(EQUIPMENT_PHOTO_PATH + ylbtp1.getOriginalFilename());
        }
        if (smtp1 != null) {
            entity.setSmtp(EQUIPMENT_PHOTO_PATH + smtp1.getOriginalFilename());
        }
        if (azysytp1 != null) {
            entity.setAzysytp(EQUIPMENT_PHOTO_PATH + azysytp1.getOriginalFilename());
        }
        if (xntp1 != null) {
            entity.setXntp(EQUIPMENT_PHOTO_PATH + xntp1.getOriginalFilename());
        }
        if (fltp1 != null) {
            entity.setFltp(EQUIPMENT_PHOTO_PATH + fltp1.getOriginalFilename());
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
        // 判断是否登录
        if (userController.judgeNotLogin(request)) {
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
    public ApiResult<Object> findAllByGsidByPage(HttpServletRequest request, int page, int size) {
        // 判断是否登录
        if (userController.judgeNotLogin(request)) {
            return ApiResult.FAILURE("未登录");
        }
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<EquipmentEntity> list = equipmentRepository.findAllByGsidOrderByCjsjDesc(userRepository.findByUuid(request.getHeader("uuid")).getGsid(), pageable);

        return ApiResult.SUCCESS(list);

    }

}
