package com.chiy.rfgc.controller;

import com.chiy.rfgc.common.ApiResult;
import com.chiy.rfgc.entity.EquipmentEntity;
import com.chiy.rfgc.entity.ProductdetailsEntity;
import com.chiy.rfgc.repository.EquipmentRepository;
import com.chiy.rfgc.repository.EquipmentTypeRepository;
import com.chiy.rfgc.repository.ProductDetailsRepository;
import com.chiy.rfgc.repository.UserRepository;
import com.chiy.rfgc.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

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
    @Resource
    private ProductDetailsRepository productDetailsRepository;

    private static final String EQUIPMENT_PHOTO_PATH = "/equipment/";

    @ApiOperation(value = "添加")
    @RequestMapping("/add")
    public ApiResult<Object> add(String uuid, EquipmentEntity entity, HttpServletRequest request, MultipartFile file, List<ProductTitle> contents) throws IOException {
//        String uuid = userController.getUuid(request);
//        // 判断是否登录
//        if ("".equals(uuid)) {
//            return ApiResult.UNKNOWN();
//        }
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
        // 遍历添加小标题及内容
        if (contents.size() != 0) {
            for (ProductTitle title : contents) {
                ProductdetailsEntity entity2 = new ProductdetailsEntity();
                entity2.setBt(title.getTitle());
                entity2.setXxnr(title.getContent());
                entity2.setCpid(entity1.getId());
                productDetailsRepository.save(entity2);
            }
        }
        return ApiResult.SUCCESS(entity1);
    }

    @ApiOperation("修改")
    @RequestMapping("/update")
    public ApiResult<Object> update(HttpServletRequest request, EquipmentEntity entity, List<ProductdetailsEntity> list, MultipartFile file) throws IOException {
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
        // 修改小标题
        for (ProductdetailsEntity entity2 : list) {
            ProductdetailsEntity entity3 = productDetailsRepository.findById(entity2.getId());
            if (entity3 == null) {
                return ApiResult.FAILURE("不存在");
            }
            productDetailsRepository.save(entity2);
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
        // 删除小标题
        boolean flag = productDetailsRepository.deleteByCpId(id);
        if (flag) {
            return ApiResult.SUCCESS("删除成功");
        }
        return ApiResult.SUCCESS("删除失败");
    }

    @ApiOperation("通过公司id查询分页显示")
    @RequestMapping("/findAllByGsidByPage")
    public ApiResult<Object> findAllByGsidByPage(HttpServletRequest request, Integer sblx) {
        String uuid = userController.getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResult.UNKNOWN();
        }
        if (sblx == null) {
            return ApiResult.FAILURE("设备类型不能为空");
        }
//        Pageable pageable = PageRequest.of(page - 1, size);

        List<EquipmentEntity> list = equipmentRepository.findAllByGsidAndSblxOrderByCjsjDesc(userRepository.findByUuid(uuid).getGsid(), sblx);


        return ApiResult.SUCCESS(addMap(list));

    }

    @ApiOperation("前端显示公司信息")
    @RequestMapping("/findAllByGsid")
    public ApiResult<Object> findAllByGsid(Integer gsid, Integer sblx) {
        if (gsid == null) {
            return ApiResult.FAILURE("公司id不能为空");
        }
        if (sblx == null) {
            return ApiResult.FAILURE("设备类型不能为空");
        }

        List<EquipmentEntity> list = equipmentRepository.findAllByGsidAndSblxOrderByCjsjDesc(gsid, sblx);

        return ApiResult.SUCCESS(addMap(list));
    }

    @ApiOperation("通过id查询")
    @RequestMapping("/findById")
    public ApiResult<Object> findById(HttpServletRequest request, Integer id) {
        String uuid = userController.getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResult.UNKNOWN();
        }
        if (id == null) {
            return ApiResult.FAILURE("id不能为空");
        }
        EquipmentEntity equipmentEntity = equipmentRepository.findById(id);
        List<EquipmentEntity> list = new ArrayList<>();
        list.add(equipmentEntity);
        return ApiResult.SUCCESS(addMap(list));
    }

    public List<Map> addMap(List<EquipmentEntity> list) {
        List<Map> result = new ArrayList<>();
        for (EquipmentEntity equipmentEntity : list) {
            List<ProductdetailsEntity> cpxq = productDetailsRepository.findAllByCpid(equipmentEntity.getId());
            Map<String, Object> map = new HashMap<>();
            map.put("id", equipmentEntity.getId());
            map.put("gsid",equipmentEntity.getGsid());
            map.put("cpmc",equipmentEntity.getCpmc());
            map.put("cptp",equipmentEntity.getCptp());
            map.put("cpxq",cpxq);
            result.add(map);
        }
        return result;
    }

    static class ProductTitle {
        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

}


