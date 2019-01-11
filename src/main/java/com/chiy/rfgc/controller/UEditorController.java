package com.chiy.rfgc.controller;
import com.baidu.ueditor.ActionEnter;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

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


    @RequestMapping(value="/show")
    public void show(String filename, HttpServletResponse response) throws IOException {
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