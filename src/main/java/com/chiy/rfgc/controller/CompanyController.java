package com.chiy.rfgc.controller;

import com.chiy.rfgc.common.ApiResult;
import com.chiy.rfgc.repository.CompanyRepository;
import com.chiy.rfgc.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(description = "公司管理")
@RestController
@RequestMapping(value = "/company", method = {RequestMethod.GET, RequestMethod.POST})
public class CompanyController {

    @Resource
    private CompanyRepository companyRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserController userController;

    @ApiOperation(value = "通过公司id查询公司名称")
    @RequestMapping("/findGsmcByGsid")
    public ApiResult<Object> find(HttpServletRequest request) {
        String uuid = userController.getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResult.UNKNOWN();
        }
        String gsmc = companyRepository.findById(userRepository.findByUuid(uuid).getGsid()).getGsmc();
        return ApiResult.SUCCESS(gsmc);
    }
}
