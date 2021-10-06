package com.henrysican.rentaspot.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class AmazonS3Service {

    private final String bucketName;
    private final AmazonS3 s3;

    @Autowired
    public AmazonS3Service(@Value("${cloud.aws.bucket-name}") String bucketName, AmazonS3 s3) {
        this.bucketName = bucketName;
        this.s3 = s3;
    }

    /**
     * Saves the given file in the provided directory to the S3 bucket.
     * @param dir   the directory to save the file in
     * @param file  the file to be saved
     */
    public void uploadFile(String dir, MultipartFile file){
        String path = bucketName + "/" + dir;
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        try {
            s3.putObject(path, fileName, file.getInputStream(), objectMetadata);
        } catch (IOException | AmazonServiceException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the file with the given name and directory location.
     * @param dir       the directory of the file
     * @param fileName  the file to be deleted
     */
    public void deleteFile(String dir, String fileName){
        s3.deleteObject(bucketName + "/" + dir, fileName);
    }
}