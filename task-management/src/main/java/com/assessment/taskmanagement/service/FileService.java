package com.assessment.taskmanagement.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {

    @Value("${file.path}")
    private String filePath;

    public String uploadFile(Long id, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String path = null;
        path = filePath + id + extension;
        Files.copy(file.getInputStream(), Paths.get(path),
                StandardCopyOption.REPLACE_EXISTING);
        return path;

    }
}
