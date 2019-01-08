package com.chiy.rfgc.controller;
import com.baidu.ueditor.ActionEnter;
import com.chiy.rfgc.common.ApiResult;
import com.chiy.rfgc.config.Ueditor;
import com.chiy.rfgc.entity.FileEntity;
import com.chiy.rfgc.repository.FileRepository;
import com.chiy.rfgc.repository.UserRepository;
import com.chiy.rfgc.utils.FileUtils;
import io.swagger.annotations.ApiOperation;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@RestController
public class UEditorController {
    @Autowired
    private HttpServletRequest request;
    @Resource
    private UserController userController;

    private static final String PRODUCT_PHOTO_PATH = "/product/";

    @RequestMapping("/ueditorConfig")
    public void getUEditorConfig(HttpServletResponse response) {
        String rootPath = "src/main/resources/static";
        try {
            String exec = new ActionEnter(request, rootPath).exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @RequestMapping(value = "/imgUpload")
//    public Ueditor imgUpload(MultipartFile upfile) {
//        Ueditor ueditor = new Ueditor();
//        return ueditor;
//    }


    @ApiOperation("添加图片")
    @RequestMapping("/uploadimage")
    public ApiResult<Object> addPhoto(HttpServletRequest request, MultipartFile upfile) throws IOException {
        String wjlj = PRODUCT_PHOTO_PATH;
        String uuid = userController.getUuid(request);
        // 判断是否登录
        if ("".equals(uuid)) {
            return ApiResult.UNKNOWN();
        }
        if (upfile != null) {
            FileUtils.addPhoto(request, wjlj, upfile);
        }

        return ApiResult.SUCCESS(wjlj + upfile.getOriginalFilename());
    }

}