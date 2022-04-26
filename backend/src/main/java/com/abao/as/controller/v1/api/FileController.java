package com.abao.as.controller.v1.api;

import com.abao.as.dto.response.Response;
import com.abao.as.service.common.FileService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.font.FileFontStrike;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author duofan
 * @version 1.0
 * @email 2441051071@qq.com
 * @website duofan.top
 * @date 2022/3/11
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/file")
@Api(value = "weichou-application", description = "文件上传API操作接口")
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "文件上传", notes = "文件上传", authorizations = {@Authorization(value = "apiKey")})
    @PostMapping(value = "/upload")
    public Response upload(MultipartFile[] files) {
        return Response.ok().setData(fileService.upload(files));
    }

    @Value("${user.file.rootPath}")
    private String rootPath;

    @Value("${user.file.url}")
    private String prefixPath;

    @PostMapping(value = "/upload/novel")
    @ApiOperation(value = "小说上传", notes = "小说上传")
    public Response upload( @ApiParam(value = "上传的文件", required = true)  @RequestParam("file") MultipartFile file) {
        // 测试MultipartFile接口的各个方法
        System.out.println("文件类型ContentType=" + file.getContentType());
        System.out.println("文件组件名称Name=" + file.getName());
        System.out.println("文件原名称OriginalFileName=" + file.getOriginalFilename());
        System.out.println("文件大小Size=" + file.getSize() / 1024 + "KB");
        try {
            if (file.isEmpty()) {
                return Response.error();
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();
            log.info("上传的文件名为：" + fileName);
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            log.info("文件的后缀名为：" + suffixName);

            // 获取文件相对类路径
            //String filePath = request.getServletContext().getRealPath("/");
            //文件绝对路径,项目中一般使用相对类路径,即使文件变更路径也会跟着变
            String filePath = rootPath;
            System.out.println("path = " + filePath);
            //构造一个路径
            String newImg = UUID.randomUUID() + suffixName;
            String path = filePath + newImg;
            log.info("构造路径" + path);

            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入

            return Response.ok();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.ok();
    }

}
