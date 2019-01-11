package com.chiy.rfgc.controller;
import com.baidu.ueditor.ActionEnter;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UEditorController {

    @RequestMapping(value="/config")
    public void config(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
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



    @RequestMapping(value = "/upload")
    public Map upload(HttpServletRequest request, HttpServletResponse response, MultipartFile file) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        Map<String, String> result = new HashMap<>();
        try {
            String wjlj = "/product/";
            String path = request.getSession().getServletContext().getRealPath(wjlj);
            String realPath = System.currentTimeMillis() + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
            File dest = new File(path + realPath);
            if(!dest.getParentFile().exists()){
                dest.getParentFile().mkdir();
            }
            file.transferTo(dest);

            result.put("state", "SUCCESS");
            result.put("title", file.getName());
            result.put("url", realPath);
            result.put("original", file.getName());
        } catch (IOException e) {
            result.put("state", "图片上传失败");
        }
        return result;
    }

    @RequestMapping(value="/show")
    public void show(String filename, HttpServletResponse response) throws IOException {
//        String name = System.currentTimeMillis() + filename.substring(filename.indexOf("."));
        File file = new File("D:/image/" + filename);
        response.setHeader("content-disposition", "attachment;filename=" + filename);
        response.setCharacterEncoding("UTF-8");
        InputStream is = new FileInputStream(file);
        int len = 0;
        byte[] data = new byte[1024];
        OutputStream os = response.getOutputStream();
        while ((len = is.read(data)) > 0) {
            os.write(data, 0, len);
        }
        os.flush();
        os.close();
        is.close();
    }



}