package com.henrysican.rentaspot.services;

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

    /**
     * Saves the given file in the provided directory to the base directory.
     * @param dir   the directory to save the file in
     * @param file  the file to be saved
     */
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
        System.out.println("*****");
        System.out.println("*****");
        System.out.println("srvc FILE PATH: "+newFilePath);
        System.out.println("*****");
        System.out.println("*****");
        try {
            long bytesWritten = Files.copy(file.getInputStream(), newFilePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("*****");
            System.out.println("*****");
            System.out.println("srvc BYTES WRITTEN: "+bytesWritten);
            System.out.println("*****");
            System.out.println("*****");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the file with the given name and directory location.
     * @param dir       the directory of the file
     * @param fileName  the file to be deleted
     */
    public void deleteFile(String dir, String fileName){
        Path filePath = Paths.get(uploadDir + File.separator + dir + File.separator + fileName);
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
