package com.example.admin.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileServiceImpl implements FileService {
    
    @Value("${resource.local_files_path}")
    public String base_path;

    
    public Path local_path(String username, String type){
        log.info(username);
        log.info(type);
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

    public List<String> listDirs(String username, String type){
        List<String> files = new ArrayList<String>();
        Path path = local_path(username, type);
        File root = new File(path.toUri());
        File[] dirs = root.listFiles(File::isDirectory);
        for(File dir : dirs){
            files.add(dir.getName());
        }
        return files;
    }

    public List<FileInfo> getFileInfos(String username, String type){
        List<String> files = listFiles(username, type);
        return files.parallelStream().map(f -> getFileInfo(username, type, f))
                              .collect(Collectors.toList());
    }

    public FileInfo getFileInfo(String username, String type, String filename){
        Path path = local_path(username, type);
        try {
            long size = Files.size(path.resolve(filename));
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long time = Files.getLastModifiedTime(path.resolve(filename)).toMillis();
            String date = dateFormat.format(time);
            FileInfo fileInfo = new FileInfo();
            fileInfo.setTime(date);
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

    public boolean deleteDir(String username, String type, String dirname){
        Path local = local_path(username, type);
        Path root = local.resolve(dirname);
        try{
            Files.walk(root)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .peek(System.out::println)
                .forEach(File::delete);;
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String username, String type, String filename){
        Path dir = local_path(username, type);
        Path file_path = dir.resolve(filename);
        log.info("delete:{},{},{},{}",username,filename,dir.toString(),file_path.toString());
        try{
            boolean flag = Files.deleteIfExists(file_path);
            return flag;
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete_abs(String userRoot, String type, String filename) {
        Path dir = Paths.get(userRoot, type).toAbsolutePath().normalize();
        Path file_path = dir.resolve(filename);
        log.info("delete:{}", file_path.toString());
        try {
            boolean flag = Files.deleteIfExists(file_path);
            return flag;
        } catch (Exception e) {
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