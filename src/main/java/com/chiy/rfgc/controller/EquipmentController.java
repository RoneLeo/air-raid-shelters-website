package com.chiy.rfgc.controller;

import com.alibaba.fastjson.JSONObject;
import com.chiy.rfgc.common.ApiResult;
import com.chiy.rfgc.common.ApiResultPage;
import com.chiy.rfgc.config.PhotoTypeAndPath;
import com.chiy.rfgc.entity.EquipmentEntity;
import com.chiy.rfgc.entity.ProductdetailsEntity;
import com.chiy.rfgc.repository.EquipmentRepository;
import com.chiy.rfgc.repository.EquipmentTypeRepository;
import com.chiy.rfgc.repository.ProductDetailsRepository;
import com.chiy.rfgc.repository.UserRepository;
import com.chiy.rfgc.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @ApiOperation(value = "添加")
    @RequestMapping("/add")
    public ApiResult<Object> add(EquipmentEntity entity, HttpServletRequest request, MultipartFile file, String contents) throws IOException {
        String uuid = userController.getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResult.UNKNOWN();
        }
        // 判断设备类型
        if (entity.getSblx() == null || equipmentTypeRepository.findById(entity.getSblx()) == null) {
            return ApiResult.FAILURE("添加失败，设备类型不能为空或该设备类型不存在");
        }
        //
        List<ProductTitle> list = JSONObject.parseArray(contents, ProductTitle.class);
        // 添加图片
        if (file != null) {
            String path = FileUtils.addPhoto(request, PhotoTypeAndPath.EQUIPMENT_PHOTO_PATH, file);
            entity.setCptp(PhotoTypeAndPath.EQUIPMENT_PHOTO_PATH + path);
        }
        entity.setGsid(userRepository.findByUuid(uuid).getGsid());
        entity.setCjsj(new Date());
        EquipmentEntity entity1 = equipmentRepository.save(entity);
        if (entity1 == null) {
            return ApiResult.FAILURE("添加失败");
        }
        // 遍历添加小标题及内容
        if (list.size() != 0) {
            for (ProductTitle title : list) {
                ProductdetailsEntity entity2 = new ProductdetailsEntity();
                entity2.setBt(title.getBt());
                entity2.setXxnr(title.getXxnr());
                entity2.setCpid(entity1.getId());
                productDetailsRepository.save(entity2);
            }
        }
        return ApiResult.SUCCESS(entity1);
    }

    @ApiOperation("修改")
    @RequestMapping("/update")
    public ApiResult<Object> update(HttpServletRequest request, EquipmentEntity entity, String contents, MultipartFile file) throws IOException {
        String uuid = userController.getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResult.UNKNOWN();
        }
        // 判断设备类型
        if (entity.getSblx() == null || equipmentTypeRepository.findById(entity.getSblx()) == null) {
            return ApiResult.FAILURE("修改失败，设备类型不能为空或该设备类型不存在");
        }
        //
        List<ProductdetailsEntity> list = JSONObject.parseArray(contents, ProductdetailsEntity.class);
        // 添加图片
        if (file != null) {
            String path = FileUtils.addPhoto(request, PhotoTypeAndPath.EQUIPMENT_PHOTO_PATH, file);
            entity.setCptp(PhotoTypeAndPath.EQUIPMENT_PHOTO_PATH + path);
        }
        EquipmentEntity entity1 = equipmentRepository.save(entity);
        if (entity1 == null) {
            return ApiResult.FAILURE("修改失败");
        }
        // 修改小标题
        List<Integer> idList = productDetailsRepository.findAllIdByCpid(entity1.getId());
        for (ProductdetailsEntity entity2 : list) {
            ProductdetailsEntity entity3 = null;
            // 如果id存在，则修改
            if (entity2.getId() != null) {
                entity3 = productDetailsRepository.findById(entity2.getId());
                if (entity3 == null) {
                    return ApiResult.FAILURE("不存在，修改失败");
                }
                entity2.setCpid(entity1.getId());
                productDetailsRepository.save(entity2);
            } else if (entity2.getId() == null) {
                // 如果id不存在，则添加
                entity3 = new ProductdetailsEntity();
                entity3.setCpid(entity1.getId());
                entity3.setBt(entity2.getBt());
                entity3.setXxnr(entity2.getXxnr());
                productDetailsRepository.save(entity3);
            }
            // 筛选删除的
            Iterator<Integer> iterator = idList.iterator();
            if (idList.size() != 0) {
                while (iterator.hasNext()) {
                    Integer value = iterator.next();
                        if (value == entity2.getId()) {
                            iterator.remove();
                        }
                }
            }

        }
        // 删除修改删除的
        for (Integer id : idList) {
            int result = productDetailsRepository.deleteById(id);
            if (result == 0) {
                return ApiResult.FAILURE("删除失败");
            }
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
        int flag = productDetailsRepository.deleteByCpId(id);
        if (flag == 0) {
            return ApiResult.SUCCESS("删除失败");
        }
        return ApiResult.SUCCESS("删除成功");
    }

    @ApiOperation("后台通过公司id和设备类型查询")
    @RequestMapping("/findAllByGsidAndSblx")
    public ApiResultPage<Object> findAllByGsidAndSblx(HttpServletRequest request, Integer sblx, int page, int size) {
        String uuid = userController.getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResultPage.UNKNOWN();
        }
        if (sblx == null) {
            return ApiResultPage.FAILURE("设备类型不能为空");
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<EquipmentEntity> list = equipmentRepository.findAllByGsidAndSblxOrderByCjsjDesc(userRepository.findByUuid(uuid).getGsid(), sblx, pageable);

        List<EquipmentEntity> list1 = equipmentRepository.findAllByGsidAndSblxOrderByCjsjDesc(userRepository.findByUuid(uuid).getGsid(), sblx);
        int totalElements = list1.size();
        int num = totalElements % size;
        int totalPages = 0;
        if (num == 0) {
            totalPages = totalElements/size;
        } else {
            totalPages = totalElements/size + 1;
        }
        return ApiResultPage.SUCCESS(addMap(list), page, size, totalElements, totalPages);
    }

    @ApiOperation("前端通过公司id和设备类型查询")
    @RequestMapping("/frontFindAllByGsidAndSblx")
    public ApiResultPage<Object> frontFindAllByGsidAndSblx(Integer gsid, Integer sblx, int page, int size) {
        if (gsid == null) {
            return ApiResultPage.FAILURE("公司id不能为空");
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<EquipmentEntity> list = null;
        if (sblx == null) {
            list = equipmentRepository.findAllByGsid(gsid, pageable);
        } else {
            list = equipmentRepository.findAllByGsidAndSblxOrderByCjsjDesc(gsid, sblx, pageable);
        }
        List<EquipmentEntity> list1 = equipmentRepository.findAllByGsidAndSblxOrderByCjsjDesc(gsid, sblx);
        int totalElements = list1.size();
        int num = totalElements % size;
        int totalPages = 0;
        if (num == 0) {
            totalPages = totalElements/size;
        } else {
            totalPages = totalElements/size + 1;
        }
        return ApiResultPage.SUCCESS(addMap(list), page, size, totalElements, totalPages);
    }

    @ApiOperation("后台通过id查询")
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
        List<EquipmentEntity> list = new ArrayList<>();
        EquipmentEntity entity = equipmentRepository.findById(id);
        if (entity == null) {
            return ApiResult.FAILURE("不存在");
        }
        list.add(entity);
        return ApiResult.SUCCESS(addMap(list));
    }

    @ApiOperation("前端通过id查询")
    @RequestMapping("/frontFindById")
    public ApiResult<Object> findById(Integer id) {
        if (id == null) {
            return ApiResult.FAILURE("id不能为空");
        }
        List<EquipmentEntity> list = new ArrayList<>();
        EquipmentEntity entity = equipmentRepository.findById(id);
        if (entity == null) {
            return ApiResult.FAILURE("不存在");
        }
        list.add(entity);
        return ApiResult.SUCCESS(addMap(list));
    }

    public List<Map> addMap(Page<EquipmentEntity> list) {
        List<Map> result = new ArrayList<>();
        for (EquipmentEntity equipmentEntity : list) {
            List<ProductdetailsEntity> cpxq = productDetailsRepository.findAllByCpid(equipmentEntity.getId());
            Map<String, Object> map = new HashMap<>();
            map.put("id", equipmentEntity.getId());
            map.put("gsid",equipmentEntity.getGsid());
            map.put("cpmc",equipmentEntity.getCpmc());
            map.put("cptp",equipmentEntity.getCptp());
            map.put("sblx", equipmentEntity.getSblx());
            map.put("cpxq",cpxq);
            result.add(map);
        }
        return result;
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
            map.put("sblx", equipmentEntity.getSblx());
            map.put("cpxq",cpxq);
            result.add(map);
        }
        return result;
    }

    static class ProductTitle {
        private String bt;
        private String xxnr;

        public String getBt() {
            return bt;
        }

        public void setBt(String bt) {
            this.bt = bt;
        }

        public String getXxnr() {
            return xxnr;
        }

        public void setXxnr(String xxnr) {
            this.xxnr = xxnr;
        }
    }

}


