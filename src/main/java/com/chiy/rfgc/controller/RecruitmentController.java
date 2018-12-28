package com.chiy.rfgc.controller;

import com.chiy.rfgc.common.ApiResult;
import com.chiy.rfgc.entity.NetserviceEntity;
import com.chiy.rfgc.entity.ProjectcaseEntity;
import com.chiy.rfgc.entity.RecruitmentEntity;
import com.chiy.rfgc.repository.CompanyRepository;
import com.chiy.rfgc.repository.RecruitmentRepository;
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
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Api(description = "人才招聘")
@RestController
@RequestMapping(value = "/recruitment", method = {RequestMethod.GET, RequestMethod.POST})
public class RecruitmentController {

    @Resource
    private RecruitmentRepository recruitmentRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private CompanyRepository companyRepository;
    @Resource
    private UserController userController;

    @ApiOperation(value = "添加")
    @RequestMapping("/add")
    public ApiResult<Object> add(HttpServletRequest request, RecruitmentEntity entity) {
        // 判断是否登录
        if (userController.judgeNotLogin(request)) {
            return ApiResult.FAILURE("未登录");
        }
        // 判断公司id
        if (entity.getGsid() == null || companyRepository.findById(entity.getGsid()) == null) {
            return ApiResult.FAILURE("添加失败，公司id为空或者不存在");
        }
        //
        entity.setCjsj(new Date());
        RecruitmentEntity entity1 = recruitmentRepository.save(entity);
        if (entity1 == null) {
            return ApiResult.FAILURE("添加失败");
        }
        return ApiResult.SUCCESS(entity1);
    }

    @ApiOperation("修改")
    @RequestMapping("/update")
    public ApiResult<Object> update(HttpServletRequest request, RecruitmentEntity entity) {
        // 判断是否登录
        if (userController.judgeNotLogin(request)) {
            return ApiResult.FAILURE("未登录");
        }
        // 判断公司id
        if (entity.getGsid() == null || companyRepository.findById(entity.getGsid()) == null) {
            return ApiResult.FAILURE("修改失败，公司id为空或者不存在");
        }
        // 判断是否存在
        if (recruitmentRepository.findById(entity.getId()) == null) {
            return ApiResult.FAILURE("不存在，修改失败");
        }
        RecruitmentEntity entity1 = recruitmentRepository.save(entity);
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
        if (recruitmentRepository.findById(id) == null) {
            return ApiResult.FAILURE("不存在，修改失败");
        }
        int result = recruitmentRepository.deleteById(id);
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

        Page<RecruitmentEntity> list = recruitmentRepository.findAllByGsidOrderByCjsjDesc(userRepository.findByUuid(request.getHeader("uuid")).getGsid(), pageable);

        return ApiResult.SUCCESS(list);

    }
}
