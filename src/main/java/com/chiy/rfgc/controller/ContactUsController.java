package com.chiy.rfgc.controller;

import com.chiy.rfgc.common.ApiResult;
import com.chiy.rfgc.entity.ContactusEntity;
import com.chiy.rfgc.repository.ContactUsRepository;
import com.chiy.rfgc.repository.UserRepository;
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
import java.util.List;
import java.util.regex.Pattern;

@Api(description = "联系我们")
@RestController
@RequestMapping(value = "/contactUs", method = {RequestMethod.GET, RequestMethod.POST})
public class ContactUsController {

    @Resource
    private ContactUsRepository contactUsRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserController userController;

    @ApiOperation(value = "添加")
    @RequestMapping("/add")
    public ApiResult<Object> add(ContactusEntity entity, HttpServletRequest request) {
        String uuid = userController.getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResult.UNKNOWN();
        }
        // 验证邮编
        String post = "[1-9]\\d{5}";
        if (entity.getLxyb().length() != 6 || !Pattern.matches(post, entity.getLxyb())) {
            return ApiResult.FAILURE("请输入正确邮编");
        }
        // 验证手机号码长度不超过20
        if (entity.getLxdh().length() > 20) {
            return ApiResult.FAILURE("输入过长");
        }
//        String phone = "(\\+\\d+)?1[3458]\\d{9}$";
//        if (!Pattern.matches(phone, entity.getLxdh())) {
//            return ApiResult.FAILURE("请输入正确联系电话");
//        }
        // 验证邮箱
        String email = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        if (!Pattern.matches(email, entity.getLxyx())) {
            return ApiResult.FAILURE("请输入正确联系邮箱");
        }
        if (entity.getGslx() == null) {
            return ApiResult.FAILURE("公司类型不能为空");
        }
        entity.setGsid(userRepository.findByUuid(uuid).getGsid());
        entity.setCjsj(new Date());
        ContactusEntity entity1 = contactUsRepository.save(entity);
        if (entity1 == null) {
            return ApiResult.FAILURE("添加失败");
        }
        return ApiResult.SUCCESS(entity1);
    }

    @ApiOperation("修改")
    @RequestMapping("/update")
    public ApiResult<Object> update(HttpServletRequest request, ContactusEntity entity) {
        String uuid = userController.getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResult.UNKNOWN();
        }
        // 判断是否存在
        if (contactUsRepository.findById(entity.getId()) == null) {
            return ApiResult.FAILURE("不存在，修改失败");
        }
        // 验证邮编
        String post = "[1-9]\\d{5}";
        if (entity.getLxyb().length() != 6 || !Pattern.matches(post, entity.getLxyb())) {
            return ApiResult.FAILURE("请输入正确邮编");
        }
        // 验证手机号码长度不超过20
        if (entity.getLxdh().length() > 20) {
            return ApiResult.FAILURE("输入过长");
        }
//        // 验证手机号码
//        String phone = "(\\+\\d+)?1[3458]\\d{9}$";
//        if (!Pattern.matches(phone, entity.getLxdh())) {
//            return ApiResult.FAILURE("请输入正确联系电话");
//        }
        // 验证邮箱
        String email = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        if (!Pattern.matches(email, entity.getLxyx())) {
            return ApiResult.FAILURE("请输入正确联系邮箱");
        }
        ContactusEntity entity1 = contactUsRepository.save(entity);
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
        if (contactUsRepository.findById(id) == null) {
            return ApiResult.FAILURE("不存在，修改失败");
        }
        int result = contactUsRepository.deleteById(id);
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

        Page<ContactusEntity> list = contactUsRepository.findAllByGsidOrderByCjsjDesc(userRepository.findByUuid(uuid).getGsid(), pageable);

        return ApiResult.SUCCESS(list);
    }

    @ApiOperation("前端显示公司信息")
    @RequestMapping("/findAllByGsid")
    public ApiResult<Object> findAllByGsid(Integer gsid) {
//        Pageable pageable = PageRequest.of(page - 1, size);
        if (gsid == null) {
            return ApiResult.FAILURE("公司id不能为空");
        }
        List<ContactusEntity> list = contactUsRepository.findAllByGsidOrderByGslx(gsid);

        return ApiResult.SUCCESS(list);
    }

    @ApiOperation("查询本公司信息")
    @RequestMapping("/findByGsid")
    public ApiResult<Object> findAllByGsid(HttpServletRequest request) {
        String uuid = userController.getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResult.UNKNOWN();
        }
        List<ContactusEntity> list = contactUsRepository.findAllByGsidOrderByGslx(userRepository.findByUuid(uuid).getGsid());
        return ApiResult.SUCCESS(list);
    }

    @ApiOperation("通过id查询")
    @RequestMapping("/findById")
    public ApiResult<Object> findById(Integer id) {
        if (id == null) {
            return ApiResult.FAILURE("id不能为空");
        }
        ContactusEntity entity = contactUsRepository.findById(id);
        return ApiResult.SUCCESS(entity);
    }

}
