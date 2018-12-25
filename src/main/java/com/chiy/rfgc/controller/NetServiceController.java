package com.chiy.rfgc.controller;

import com.chiy.rfgc.common.ApiResult;
import com.chiy.rfgc.entity.NetserviceEntity;
import com.chiy.rfgc.repository.NetServiceRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@Api(description = "联系我们")
@RestController
@RequestMapping(value = "/netService", method = {RequestMethod.GET, RequestMethod.POST})
public class NetServiceController {

    @Resource
    private NetServiceRepository netServiceRepository;

    @ApiOperation(value = "添加")
    @RequestMapping("/add")
    public ApiResult<Object> add(NetserviceEntity entity) {
        //
        entity.setCjsj(new Date());
        NetserviceEntity entity1 = netServiceRepository.save(entity);
        if (entity1 == null) {
            return ApiResult.FAILURE("添加失败");
        }
        return ApiResult.SUCCESS(entity1);
    }

    @ApiOperation("修改")
    @RequestMapping("/update")
    public ApiResult<Object> update(NetserviceEntity entity) {
        // 判断是否存在
        if (netServiceRepository.findById(entity.getId()) == null) {
            return ApiResult.FAILURE("不存在，修改失败");
        }
        NetserviceEntity entity1 = netServiceRepository.save(entity);
        if (entity1 == null) {
            return ApiResult.FAILURE("修改失败");
        }
        return ApiResult.SUCCESS(entity1);
    }

    @ApiOperation("删除")
    @RequestMapping("/delete")
    public ApiResult<Object> delete(Integer id) {
        if (id == null) {
            return ApiResult.FAILURE("id不能为空");
        }
        // 查询是否存在
        if (netServiceRepository.findById(id) == null) {
            return ApiResult.FAILURE("不存在，修改失败");
        }
        int result = netServiceRepository.deleteById(id);
        if (result == 0) {
            return ApiResult.FAILURE("删除失败");
        }
        return ApiResult.SUCCESS("删除成功");
    }

    @ApiOperation("通过公司id查询分页显示")
    @RequestMapping("/findAllByGsidByPage")
    public ApiResult<Object> findAllByGsidByPage(Integer gsid, int page, int size) {
        if (gsid == null) {
            return ApiResult.FAILURE("公司id不能为空");
        }
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<NetserviceEntity> list = netServiceRepository.findAllByGsidOrderByCjsjDesc(gsid, pageable);

        return ApiResult.SUCCESS(list);

    }

}
