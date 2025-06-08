package com.sixofrods.edtech.controller;

import com.sixofrods.edtech.dto.FileUploadResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/files")
public interface FileUploadController {
    // Create - Upload single or multiple files
    @PostMapping("/upload")
    ResponseEntity<List<FileUploadResponse>> uploadFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam(defaultValue = "system") String uploadedBy,
            @RequestParam(required = true) String language);

    // Read - Get list of all files (legacy endpoint)
    @GetMapping
    ResponseEntity<List<FileUploadResponse>> getAllFiles(
            @RequestParam(required = false) String language);

    // Read - Get list of all files (new endpoint)
    @GetMapping("/all")
    ResponseEntity<List<FileUploadResponse>> getAllFilesAlternative(
            @RequestParam(required = false) String language);

    // Read - Get file metadata
    @GetMapping("/{id}/info")
    ResponseEntity<FileUploadResponse> getFileInfo(@PathVariable Long id);

    // Read - View file directly in browser
    @GetMapping("/{id}/content")
    ResponseEntity<Resource> viewFile(@PathVariable Long id);

    // Update - Update an existing file
    @PutMapping("/{id}")
    ResponseEntity<FileUploadResponse> updateFile(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam(defaultValue = "system") String uploadedBy,
            @RequestParam(required = true) String language);

    // Delete - Delete a file
    @DeleteMapping("/{id}")
    ResponseEntity<Map<String, String>> deleteFile(@PathVariable Long id);
}
