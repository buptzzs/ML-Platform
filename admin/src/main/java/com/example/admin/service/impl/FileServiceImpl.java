package com.example.admin.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.admin.entity.FileInfo;
import com.example.admin.service.FileService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {
    
    @Value("${resource.local_files_path}")
    private String base_path;

    private Path local_path(String username, String type){
        Path path = Paths.get(base_path, username, type).toAbsolutePath().normalize();

        try {
            Files.createDirectories(path);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return path;
    }

    public List<String> listFiles(String username, String type){
        List<String> files = new ArrayList<String>();
        Path path = local_path(username, type);
        try {
            Stream<Path> paths =  Files.walk(path);
            files = paths.filter(Files::isRegularFile)
                        .map(p -> p.getFileName().toString())
                        .collect(Collectors.toList());
            paths.close();                      
        } catch (Exception e){
            e.printStackTrace();
        }
        return files;
    }

    public List<FileInfo> getFileInfos(String username, String type){
        List<String> files = listFiles(username, type);
        return files.parallelStream().map(f -> getFileInfo(username, type, f))
                              .collect(Collectors.toList());
    }

    private FileInfo getFileInfo(String username, String type, String filename){
        Path path = local_path(username, type);
        try {
            long size = Files.size(path.resolve(filename));
            FileInfo fileInfo = new FileInfo();
            fileInfo.setName(filename);
            fileInfo.setSize(size);
            return fileInfo;
        } catch(IOException e){
            e.printStackTrace();
            return null;
        }

    }

    public boolean store(MultipartFile file, String username, String type){
        Path dir = local_path(username, type);
        Path targetLocation = dir.resolve(file.getOriginalFilename());
        try{
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String username, String type, String filename){
        Path dir = local_path(username, type);
        Path file_path = dir.resolve(filename);
        try{
            boolean flag = Files.deleteIfExists(file_path);
            return flag;
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Resource loadAsResource(String username, String type, String filename){
        try{
            Path dir = local_path(username, type);
            Path path = dir.resolve(filename);
            Resource resource =  new UrlResource(path.toUri());
            return resource;
        }catch(MalformedURLException e){
            e.printStackTrace();
            return null;
        }
    }

}