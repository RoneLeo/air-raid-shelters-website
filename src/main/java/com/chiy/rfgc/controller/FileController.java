package com.chiy.rfgc.controller;

import com.chiy.rfgc.common.ApiResult;
import com.chiy.rfgc.entity.FileEntity;
import com.chiy.rfgc.repository.FileRepository;
import com.chiy.rfgc.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@Api(description = "文件管理")
@RestController
@RequestMapping(value = "/file", method = {RequestMethod.GET, RequestMethod.POST})
public class FileController {

    @Resource
    private FileRepository fileRepository;
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserController userController;

    private static final String CERTIFICATE_PATH = "/certificate/";
    private static final String PHOTO_PATH = "/photo/";

    @ApiOperation("添加")
    @RequestMapping("/add")
    public ApiResult<Object> add(HttpServletRequest request, FileEntity entity, MultipartFile file) throws IOException {
        String uuid = userController.getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResult.UNKNOWN();
        }
        if (entity.getWjlx() == null) {
            return ApiResult.FAILURE("添加失败，文件类型不能为空");
        }
        // 文件不能为空
        if (file == null) {
            return ApiResult.FAILURE("未选择文件");
        }
        String wjlj = "";
        if (entity.getWjlx() == 1) {
            wjlj = CERTIFICATE_PATH;
        } else {
            wjlj = PHOTO_PATH;
        }
        String path = request.getSession().getServletContext().getRealPath(wjlj);
        File dest = new File(path + file.getOriginalFilename());
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        file.transferTo(dest);

        entity.setGsid(userRepository.findByUuid(uuid).getGsid());
        entity.setWjlj(path + file.getOriginalFilename());
        entity.setCjsj(new Date());
        FileEntity entity1 = fileRepository.save(entity);
        if (entity1 == null) {
            return ApiResult.FAILURE("添加失败");
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
        if (fileRepository.findById(id) == null) {
            return ApiResult.FAILURE("不存在，修改失败");
        }
        int result = fileRepository.deleteById(id);
        if (result == 0) {
            return ApiResult.FAILURE("删除失败");
        }
        return ApiResult.SUCCESS("删除成功");
    }

    @ApiOperation("通过公司id查询分页显示")
    @RequestMapping("/findAllByGsidByPage")
    public ApiResult<Object> findAllByGsidByPage(HttpServletRequest request, @RequestParam @ApiParam(value = "1-证书， 2-图片") Integer wjlx, int page, int size) {
        String uuid = userController.getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResult.UNKNOWN();
        }
        if (wjlx == null) {
            return ApiResult.FAILURE("文件类型不能为空");
        }
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<FileEntity> list = fileRepository.findAllByGsidAndWjlxOrderByCjsjDesc(userRepository.findByUuid(uuid).getGsid(), wjlx, pageable);

        return ApiResult.SUCCESS(list);

    }

}
