package com.chiy.rfgc.controller;

import com.chiy.rfgc.common.ApiResult;
import com.chiy.rfgc.entity.CompanyEntity;
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
import javax.servlet.http.HttpSession;
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
        String uuid = getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResult.UNKNOWN();
        }
        // 判断账号是否存在
        if (userRepository.findByZh(entity.getZh()) != null) {
            return ApiResult.FAILURE("账号已存在");
        }
        entity.setGsid(userRepository.findByUuid(uuid).getGsid());
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
    public ApiResult<Object> login(String zh, String mm, HttpSession session) throws Exception {
        if (StringUtils.isEmpty(zh) || StringUtils.isEmpty(mm)) {
            return ApiResult.FAILURE("账号或密码不能为空");
        }
        UserEntity entity = userRepository.findByZhAndMm(zh, MD5Utils.getMD5(mm));
        if (entity == null) {
            return ApiResult.FAILURE("登录失败，账号或密码不正确");
        }
        session.setAttribute("Admin", entity.getZh());
        session.setAttribute("uuid", entity.getUuid());
        return ApiResult.SUCCESS(entity);
    }

    @ApiOperation("修改")
    @RequestMapping("/update")
    public ApiResult<Object> update(UserEntity entity, HttpServletRequest request) throws Exception {
        String uuid = getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResult.UNKNOWN();
        }
        // 判断是否存在
        if (userRepository.findByUuid(entity.getUuid()) == null) {
            return ApiResult.FAILURE("修改失败，不存在");
        }
        UserEntity entity1 = userRepository.save(entity);
        if (entity1 == null) {
            return ApiResult.FAILURE("修改失败");
        }
        return ApiResult.SUCCESS(entity1);
    }

    @ApiOperation("重置密码")
    @RequestMapping("/resetMm")
    public ApiResult<Object> resetMm(String id, HttpServletRequest request) throws Exception {
        String uuid = getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResult.UNKNOWN();
        }
        UserEntity entity = userRepository.findByUuid(id);
        if (StringUtils.isEmpty(id) || entity == null) {
            return ApiResult.FAILURE("id不能为空或该用户不存在");
        }
        entity.setMm(MD5Utils.getMD5("123456"));
        UserEntity entity1 = userRepository.save(entity);
        if (entity1 == null) {
            return ApiResult.FAILURE("重置密码失败");
        }
        return ApiResult.SUCCESS("重置密码成功");
    }

    @ApiOperation("修改密码")
    @RequestMapping("/updateMm")
    public ApiResult<Object> update(String mm, HttpServletRequest request) throws Exception {
        String uuid = getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResult.UNKNOWN();
        }
        // 判断是否存在
        UserEntity entity = userRepository.findByUuid(uuid);
        if (entity == null) {
            return ApiResult.FAILURE("修改失败，不存在");
        }
        entity.setMm(MD5Utils.getMD5(mm));
        UserEntity entity1 = userRepository.save(entity);
        if (entity1 == null) {
            return ApiResult.FAILURE("修改失败");
        }
        return ApiResult.SUCCESS("修改成功");
    }

    @ApiOperation("删除")
    @RequestMapping("/delete")
    public ApiResult<Object> delete(String uuid, HttpServletRequest request) {
        String id = getUuid(request);
        // 判断是否登录
        if ("".equals(id)) {
            return ApiResult.UNKNOWN();
        }
        // 判断是否存在
        if (userRepository.findByUuid(uuid) == null) {
            return ApiResult.UNKNOWN();
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
        String uuid = getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResult.UNKNOWN();
        }
        List<UserEntity> list = userRepository.findAllByGsidOrderByCjsjDesc(userRepository.findByUuid(uuid).getGsid());
        return ApiResult.SUCCESS(list);
    }

    @ApiOperation(value = "添加公司及设置初始账户")
    @RequestMapping("/setGsAndZh")
    public ApiResult<Object> setGsAndZh(String gsmc, String zh, HttpSession session) throws Exception {
        // 判断是否登录
        String uuid = (String) session.getAttribute("uuid");
        if (StringUtils.isEmpty(uuid)) {
            return ApiResult.FAILURE("未登录");
        }
        UserEntity userEntity = userRepository.findByUuid(uuid);
        // 判断是否为admin
        if (userEntity == null || !"admin".equals(userEntity.getZh())) {
            return ApiResult.FAILURE("用户不存在或非管理员不可操作");
        }
        // 添加公司
        CompanyEntity entity = new CompanyEntity();
        entity.setGsmc(gsmc);
        CompanyEntity entity1 = companyRepository.save(entity);
        if (entity1 == null) {
            return ApiResult.FAILURE("添加失败");
        }
        // 设置初始账号
        UserEntity entity2 = new UserEntity();
        entity2.setZh(zh);
        entity2.setMm(MD5Utils.getMD5("123456"));
        entity2.setGsid(entity1.getId());
        entity2.setCjsj(new Date());
        UserEntity entity3 = userRepository.save(entity2);
        if (entity3 == null) {
            return ApiResult.FAILURE("添加失败");
        }
        return ApiResult.SUCCESS(entity3);
    }

    // 获取请求头
    public String getUuid(HttpServletRequest request) {
        String uuid = request.getHeader("uuid");
        // 判断是否登录
        if (StringUtils.isEmpty(uuid) || userRepository.findByUuid(uuid) == null) {
            return "";
        }
        return uuid;
    }




}
