package com.sixofrods.edtech.controller;

import com.sixofrods.edtech.dto.FileUploadResponse;
import com.sixofrods.edtech.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class FileUploadControllerIMPL implements FileUploadController {
    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public ResponseEntity<List<FileUploadResponse>> uploadFiles(MultipartFile[] files,
                                                              String uploadedBy,
                                                              String language) {
        List<FileUploadResponse> responses = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                responses.add(FileUploadResponse.builder()
                        .fileName(file.getOriginalFilename())
                        .uploadStatus("failed")
                        .message("Failed to upload empty file")
                        .build());
                continue;
            }

            try {
                FileUploadResponse response = fileStorageService.storeFile(file, uploadedBy, language);
                responses.add(response);
            } catch (Exception e) {
                responses.add(FileUploadResponse.builder()
                        .fileName(file.getOriginalFilename())
                        .uploadStatus("failed")
                        .message("Failed to upload file: " + e.getMessage())
                        .build());
            }
        }

        return ResponseEntity.ok(responses);
    }

    @Override
    public ResponseEntity<List<FileUploadResponse>> getAllFiles(String language) {
        try {
            List<FileUploadResponse> files;
            if (language != null && !language.isEmpty()) {
                files = fileStorageService.getFilesByLanguage(language);
            } else {
                files = fileStorageService.getAllFiles();
            }
            
            if (files.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            
            return ResponseEntity.ok(files);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<FileUploadResponse> getFileInfo(String fileName) {
        try {
            FileUploadResponse fileInfo = fileStorageService.getFileByName(fileName);
            return ResponseEntity.ok(fileInfo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Resource> viewFile(String fileName) {
        try {
            byte[] fileContent = fileStorageService.readFile(fileName);
            ByteArrayResource resource = new ByteArrayResource(fileContent);

            String contentType = determineContentType(fileName);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CACHE_CONTROL, "max-age=3600")
                    .contentLength(fileContent.length)
                    .body(resource);
        } catch (IOException | RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Resource> downloadFile(String fileName) {
        try {
            byte[] fileContent = fileStorageService.readFile(fileName);
            ByteArrayResource resource = new ByteArrayResource(fileContent);

            String decodedFileName = java.net.URLDecoder.decode(fileName, java.nio.charset.StandardCharsets.UTF_8);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + decodedFileName + "\"")
                    .contentLength(fileContent.length)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (IOException | RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<FileUploadResponse> updateFile(String fileName, MultipartFile file, String uploadedBy, String language) {
        try {
            FileUploadResponse response = fileStorageService.updateFile(fileName, file, uploadedBy, language);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> deleteFile(String fileName) {
        try {
            boolean deleted = fileStorageService.deleteFile(fileName);
            if (deleted) {
                return ResponseEntity.ok(Map.of("message", "File deleted successfully"));
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private String determineContentType(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return switch (extension) {
            case "pdf" -> "application/pdf";
            case "doc" -> "application/msword";
            case "docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "mp3" -> "audio/mpeg";
            default -> "application/octet-stream";
        };
    }
}
