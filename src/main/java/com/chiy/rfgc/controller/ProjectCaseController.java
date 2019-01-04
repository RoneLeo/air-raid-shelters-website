package com.chiy.rfgc.controller;


import com.chiy.rfgc.common.ApiResult;
import com.chiy.rfgc.entity.ProjectcaseEntity;
import com.chiy.rfgc.repository.ProjectCaseRepository;
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

@Api(description = "工程案例")
@RestController
@RequestMapping(value = "/projectCase", method = {RequestMethod.GET, RequestMethod.POST})
public class ProjectCaseController {

    @Resource
    private ProjectCaseRepository projectCaseRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserController userController;

    private static final String PROJECT_PHOTO_PATH = "/project/";

    @ApiOperation(value = "添加")
    @RequestMapping("/add")
    public ApiResult<Object> add(HttpServletRequest request, ProjectcaseEntity entity, MultipartFile file) throws IOException {
        String uuid = userController.getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResult.UNKNOWN();
        }
        // 添加图片
        if (file != null) {
            FileUtils.addPhoto(request, PROJECT_PHOTO_PATH, file);
            entity.setTp(PROJECT_PHOTO_PATH + file.getOriginalFilename());
        }
        entity.setGsid(userRepository.findByUuid(uuid).getGsid());
        entity.setCjsj(new Date());
        ProjectcaseEntity entity1 = projectCaseRepository.save(entity);
        if (entity1 == null) {
            return ApiResult.FAILURE("添加失败");
        }
        return ApiResult.SUCCESS(entity1);
    }

    @ApiOperation("修改")
    @RequestMapping("/update")
    public ApiResult<Object> update(HttpServletRequest request, ProjectcaseEntity entity, MultipartFile file) throws IOException {
        String uuid = userController.getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResult.UNKNOWN();
        }
        // 判断是否存在
        if (projectCaseRepository.findById(entity.getId()) == null) {
            return ApiResult.FAILURE("不存在，修改失败");
        }
        // 添加图片
        if (file != null) {
            FileUtils.addPhoto(request, PROJECT_PHOTO_PATH, file);
            entity.setTp(PROJECT_PHOTO_PATH + file.getOriginalFilename());
        }
        ProjectcaseEntity entity1 = projectCaseRepository.save(entity);
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
        if (projectCaseRepository.findById(id) == null) {
            return ApiResult.FAILURE("不存在，修改失败");
        }
        int result = projectCaseRepository.deleteById(id);
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

        Page<ProjectcaseEntity> list = projectCaseRepository.findAllByGsidOrderByCjsjDesc(userRepository.findByUuid(uuid).getGsid(), pageable);

        return ApiResult.SUCCESS(list);

    }

    @ApiOperation("前端显示公司信息")
    @RequestMapping("/findAllByGsid")
    public ApiResult<Object> findAllByGsid(Integer gsid, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<ProjectcaseEntity> list = projectCaseRepository.findAllByGsidOrderByCjsjDesc(gsid, pageable);

        return ApiResult.SUCCESS(list);
    }
}
