package com.sixofrods.edtech.controller;

import com.sixofrods.edtech.dto.FileUploadResponse;
import com.sixofrods.edtech.entity.FileEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/files")
public interface FileUploadController {
    // Create - Upload single or multiple files
    @PostMapping("/upload")
    public ResponseEntity<List<FileUploadResponse>> uploadFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam(defaultValue = "system") String uploadedBy,
            @RequestParam(required = true) String language);

    // Read - Get list of all files
    @GetMapping("/list")
    public ResponseEntity<List<FileEntity>> getAllFiles(
            @RequestParam(required = false) String language);


    // Read - Get file metadata
    @GetMapping("/info/{fileName}")
    public ResponseEntity<FileEntity> getFileInfo(@PathVariable String fileName);

    // Read - View file directly in browser
    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFile(@PathVariable String fileName);


    // Read - Download file as attachment
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName);

    // Update - Update an existing file
    @PutMapping("/{fileName}")
    public ResponseEntity<FileUploadResponse> updateFile(
            @PathVariable String fileName,
            @RequestParam("file") MultipartFile file,
            @RequestParam(defaultValue = "system") String uploadedBy,
            @RequestParam(required = true) String language);


    // Delete - Delete a file
    @DeleteMapping("/{fileName}")
    public ResponseEntity<Map<String, String>> deleteFile(@PathVariable String fileName);


}
