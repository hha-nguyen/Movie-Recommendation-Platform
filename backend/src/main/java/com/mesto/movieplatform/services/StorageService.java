package com.mesto.movieplatform.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class StorageService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Autowired
    private AmazonS3 s3Client;

    public String uploadFile(MultipartFile file, String folderName) {
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, folderName + fileName, fileObj));
        fileObj.delete();
        return "File uploaded: " + fileName + " successfully!";
    }

    public byte[] downloadFile(String fileName, String folderName) {
        S3Object s3Object = s3Client.getObject(bucketName, folderName + fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        byte[] content = null;
        try {
            content = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return fileName + " removed...!";
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try {
            FileOutputStream fos = new FileOutputStream(convertedFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertedFile;
    }
}
