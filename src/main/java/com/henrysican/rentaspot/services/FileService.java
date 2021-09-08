package com.henrysican.rentaspot.services;

import com.henrysican.rentaspot.dao.ImageRepo;
import com.henrysican.rentaspot.models.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Transactional
public class FileService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    public void uploadFile(String dir, MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path newDirPath = Paths.get(uploadDir + File.separator + dir);
        if (!Files.exists(newDirPath)) {
            try {
                Files.createDirectory(newDirPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Path newFilePath = newDirPath.resolve(fileName);
        try {
            Files.copy(file.getInputStream(), newFilePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(String dir, String fileName){
        Path filePath = Paths.get(uploadDir + File.separator + dir + File.separator + fileName);
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
