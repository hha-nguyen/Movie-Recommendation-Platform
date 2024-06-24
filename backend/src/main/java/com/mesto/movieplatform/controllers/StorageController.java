package com.mesto.movieplatform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mesto.movieplatform.entities.Movie;
import com.mesto.movieplatform.services.StorageService;
import com.mesto.movieplatform.services.VideoChunkService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/storage/")
@RestController
public class StorageController {

    @Autowired
    private StorageService storageService;

    @PostMapping("/upload/{folderName}")
    public ResponseEntity<String> uploadFile(@PathVariable String folderName,
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(storageService.uploadFile(file, folderName + "/"));
    }

    @GetMapping("/download/{folderName}")
    public ResponseEntity<ByteArrayResource> postMethodName(
            @PathVariable String folderName,
            @RequestParam("fileName") String fileName) {
        // TODO: process POST request
        byte[] data = storageService.downloadFile(fileName, folderName + "/");
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@RequestBody String fileName) {
        return ResponseEntity.ok(storageService.deleteFile(fileName));
    }

}
