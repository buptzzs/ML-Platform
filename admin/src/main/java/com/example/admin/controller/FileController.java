package com.example.admin.controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.admin.entity.FileInfo;
import com.example.admin.entity.Result;
import com.example.admin.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file,
                                 @RequestParam("username") String username,
                                 @RequestParam("type") String type){
        boolean flag = fileService.store(file, username, type);
        log.info("user {}, upload file", username);
        if (flag){
            return Result.success("上传文件成功");
        }
        else{
            return Result.error404("上传文件失败", "失败");
        }
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam String username, 
                                             @RequestParam String type,
                                             @RequestParam String filename,
                                             HttpServletRequest request){
            log.info("下载文件", username);                                                 
            Resource resource = fileService.loadAsResource(username, type, filename);     
            
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

            } catch (IOException e){
                log.info("Could not determine file type.");
            }
            if (contentType == null) {
            contentType = "application/octet-stream";
            }

            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

    }

    @PostMapping("/delete")
    public Result<String> delete(@RequestParam String username, 
                                 @RequestParam String type,
                                 @RequestParam String filename){
            boolean flag = fileService.delete(username, type, filename);
            if (flag) {
                return Result.success("删除文件成功");
            } else {
                return Result.error404("删除文件失败", "失败");
            }
    }

    @GetMapping("/files")
    public Result<List<FileInfo>> listFiles(@RequestParam String username, @RequestParam String type){
        List<FileInfo> files = fileService.getFileInfos(username, type);
        return Result.success(files);
    }
}