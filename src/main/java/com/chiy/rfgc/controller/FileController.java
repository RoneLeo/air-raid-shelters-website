package com.chiy.rfgc.controller;

import com.chiy.rfgc.common.ApiResult;
import com.chiy.rfgc.entity.FileEntity;
import com.chiy.rfgc.entity.NetserviceEntity;
import com.chiy.rfgc.repository.CompanyRepository;
import com.chiy.rfgc.repository.FileRepository;
import com.chiy.rfgc.repository.UserRepository;
import com.chiy.rfgc.utils.StringUtils;
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
    private CompanyRepository companyRepository;

    private static final String CERTIFICATE_PATH = "/certificate/";
    private static final String PHOTO_PATH = "/photo/";

    @ApiOperation("添加")
    @RequestMapping("/add")
    public ApiResult<Object> add(String uuid, FileEntity entity, MultipartFile file) {
        // 判断是否登录
        if (StringUtils.isEmpty(uuid) || userRepository.findById(uuid) == null) {
            return ApiResult.FAILURE("未登录");
        }
        // 判断公司id
        if (entity.getGsid() == null || companyRepository.findById(entity.getGsid()) == null) {
            return ApiResult.FAILURE("添加失败，公司id为空或者不存在");
        }
        if (entity.getWjlx() == null) {
            return ApiResult.FAILURE("添加失败，文件类型不能为空");
        }
        String wjlj = "";
        if (entity.getWjlx() == 1) {
            wjlj = CERTIFICATE_PATH;
        } else {
            wjlj = PHOTO_PATH;
        }
        entity.setWjlj(wjlj + file.getOriginalFilename());
        entity.setCjsj(new Date());
        FileEntity entity1 = fileRepository.save(entity);
        if (entity1 == null) {
            return ApiResult.FAILURE("添加失败");
        }
        return ApiResult.SUCCESS(entity1);
    }

    @ApiOperation("删除")
    @RequestMapping("/delete")
    public ApiResult<Object> delete(String uuid, Integer id) {
        // 判断是否登录
        if (StringUtils.isEmpty(uuid) || userRepository.findById(uuid) == null) {
            return ApiResult.FAILURE("未登录");
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
    public ApiResult<Object> findAllByGsidByPage(String uuid, @RequestParam @ApiParam(value = "1-证书， 2-图片") Integer wjlx, int page, int size) {
        // 判断是否登录
        if (StringUtils.isEmpty(uuid) || userRepository.findById(uuid) == null) {
            return ApiResult.FAILURE("未登录");
        }
        if (wjlx == null) {
            return ApiResult.FAILURE("文件类型不能为空");
        }
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<FileEntity> list = fileRepository.findAllByGsidAndWjlxOrderByCjsjDesc(userRepository.findById(uuid).getGsid(), wjlx, pageable);

        return ApiResult.SUCCESS(list);

    }








    /**
     * 添加文件
     */
//    public FileEntity addFile(HttpServletRequest request, MultipartFile file, Integer wjlx, Integer gsid) {
//        String fileName = file.getOriginalFilename();
//        String realPath = "";
//        if (wjlx == 1) {
//            realPath = CERTIFICATE_PATH;
//        } else {
//            realPath = PHOTO_PATH;
//        }
//        String path = request.getSession().getServletContext().getRealPath(realPath);
//        File dest = new File(path + fileName);
//        if (!dest.getParentFile().exists()) {
//            dest.getParentFile().mkdir();
//        }
//        FileEntity entity = new FileEntity();
//        try {
//            file.transferTo(dest);
//            entity.setGsid(gsid);
//            entity.setWjlx(wjlx);
//            entity.setWjmc(fileName);
//            entity.setWjlj(path + fileName);
//            entity.setCjsj(new Date());
//            fileRepository.save(entity);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return entity;
//
//    }
}
