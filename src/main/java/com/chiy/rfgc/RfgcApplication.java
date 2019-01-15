package com.chiy.rfgc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;

import javax.servlet.MultipartConfigElement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class RfgcApplication {

    public static void main(String[] args) {
        SpringApplication.run(RfgcApplication.class, args);
    }

    @Bean
    public Converter<String, Date> addNewConvert() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = sdf.parse((String) source);
                } catch (ParseException e) {
                }
                if (date == null) {
                    try {
                        date = sdf2.parse((String) source);
                    } catch (ParseException e) {
                        return null;
                    }
                }
                return date;
            }
        };
    }

//    @Bean
//    public MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        // 设置文件大小限制 ,超出设置页面会抛出异常信息，
//        // 这样在文件上传的地方就需要进行异常信息的处理了;
//        factory.setMaxFileSize("2024MB"); // KB,MB
//        /// 设置总上传数据总大小
//        factory.setMaxRequestSize("2024MB");
//        // Sets the directory location where files will be stored.
//        // factory.setLocation("路径地址");
//        return factory.createMultipartConfig();
//    }
}

