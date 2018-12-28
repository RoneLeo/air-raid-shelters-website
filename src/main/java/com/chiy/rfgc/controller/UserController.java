package com.chiy.rfgc.controller;

import com.chiy.rfgc.common.ApiResult;
import com.chiy.rfgc.entity.UserEntity;
import com.chiy.rfgc.repository.CompanyRepository;
import com.chiy.rfgc.repository.UserRepository;
import com.chiy.rfgc.utils.MD5Utils;
import com.chiy.rfgc.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Api(description = "用户管理")
@RestController
@RequestMapping(value = "/user", method = {RequestMethod.GET, RequestMethod.POST})
public class UserController {

    @Resource
    private UserRepository userRepository;
    @Resource
    private CompanyRepository companyRepository;

    @ApiOperation("添加用户")
    @RequestMapping("/add")
    public ApiResult<Object> add(UserEntity entity, HttpServletRequest request) throws Exception {
        // 判断是否登录
        if (judgeNotLogin(request)) {
            return ApiResult.FAILURE("未登录");
        }
        // 判断公司id
        if (entity.getGsid() == null || companyRepository.findById(entity.getGsid()) == null) {
            return ApiResult.FAILURE("添加失败，公司id为空或者不存在");
        }
        // 判断账号是否存在
        if (userRepository.findByZh(entity.getZh()) != null) {
            return ApiResult.FAILURE("账号已存在");
        }
        entity.setMm(MD5Utils.getMD5(entity.getMm()));
        entity.setCjsj(new Date());
        UserEntity entity1 = userRepository.save(entity);
        if (entity1 == null) {
            return ApiResult.FAILURE("添加失败");
        }
        return ApiResult.SUCCESS(entity1);
    }

    @ApiOperation("登录")
    @RequestMapping("/login")
    public ApiResult<Object> login(String zh, String mm) throws Exception {
        if (StringUtils.isEmpty(zh) || StringUtils.isEmpty(mm)) {
            return ApiResult.FAILURE("账号或密码不能为空");
        }
        UserEntity entity = userRepository.findByZhAndMm(zh, MD5Utils.getMD5(mm));
        if (entity == null) {
            return ApiResult.FAILURE("登录失败，账号或密码不正确");
        }
        return ApiResult.SUCCESS(entity);
    }

    @ApiOperation("修改")
    @RequestMapping("/update")
    public ApiResult<Object> update(UserEntity entity, HttpServletRequest request) throws Exception {
        // 判断是否登录
        // 判断是否登录
        if (judgeNotLogin(request)) {
            return ApiResult.FAILURE("未登录");
        }
        // 判断是否存在
        if (userRepository.findByUuid(entity.getUuid()) == null) {
            return ApiResult.FAILURE("修改失败，不存在");
        }
        // 判断公司id
        if (entity.getGsid() == null || companyRepository.findById(entity.getGsid()) == null) {
            return ApiResult.FAILURE("修改失败，公司id为空或者不存在");
        }
        entity.setMm(MD5Utils.getMD5(entity.getMm()));
        UserEntity entity1 = userRepository.save(entity);
        if (entity1 == null) {
            return ApiResult.FAILURE("修改失败");
        }
        return ApiResult.SUCCESS(entity1);
    }

    @ApiOperation("删除")
    @RequestMapping("/delete")
    public ApiResult<Object> delete(String uuid, HttpServletRequest request) {
        // 判断是否登录
        if (judgeNotLogin(request)) {
            return ApiResult.FAILURE("未登录");
        }
        // 通过id查询是否存在
        if (userRepository.findByUuid(uuid) == null) {
            return ApiResult.FAILURE("该用户不存在");
        }
        // 通过id删除
        int result = userRepository.deleteById(uuid);
        if (result == 0) {
            return ApiResult.FAILURE("删除失败");
        }
        return ApiResult.SUCCESS("删除成功");
    }

    @ApiOperation("通过公司id查询用户列表")
    @RequestMapping("/findAllByGsid")
    public ApiResult<Object> findAllByGsid(HttpServletRequest request) {
        // 判断是否登录
        if (judgeNotLogin(request)) {
            return ApiResult.FAILURE("未登录");
        }
        // 通过id查询是否存在
        UserEntity userEntity = userRepository.findByUuid(request.getHeader("uuid"));
        if (userEntity == null) {
            return ApiResult.FAILURE("该用户不存在");
        }
        List<UserEntity> list = userRepository.findAllByGsidOrderByCjsjDesc(userEntity.getGsid());
        return ApiResult.SUCCESS(list);
    }

    // 获取请求头
    public boolean judgeNotLogin(HttpServletRequest request) {
        String uuid = request.getHeader("uuid");
        // 判断是否登录
        if (StringUtils.isEmpty(uuid) || userRepository.findByUuid(uuid) == null) {
            return true;
        }
        return false;
    }



}
