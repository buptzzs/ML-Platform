package com.example.admin.service;

import java.util.List;

import com.example.admin.entity.FileInfo;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {


    /**
     * 获取用户文件信息
     * @param username
     * @param type
     * @return
     */
    List<FileInfo> getFileInfos(String username, String type);

    boolean store(MultipartFile file, String username, String type);

    boolean delete(String username,  String type, String filename);

    Resource loadAsResource(String username, String type, String filename);


} 