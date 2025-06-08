package com.sixofrods.edtech.controller;

import com.sixofrods.edtech.dto.FileUploadResponse;
import com.sixofrods.edtech.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
        if (files == null || files.length == 0) {
            return ResponseEntity.badRequest().body(List.of(FileUploadResponse.builder()
                    .fileName("N/A")
                    .uploadStatus("failed")
                    .message("No files were provided")
                    .build()));
        }

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
            
            return ResponseEntity.ok(files); // Always return OK with empty list if no files
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<List<FileUploadResponse>> getAllFilesAlternative(String language) {
        return getAllFiles(language); // Reuse the same implementation
    }

    @Override
    public ResponseEntity<FileUploadResponse> getFileInfo(Long id) {
        try {
            FileUploadResponse fileInfo = fileStorageService.getFileById(id);
            return ResponseEntity.ok(fileInfo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Resource> viewFile(Long id) {
        try {
            byte[] fileContent = fileStorageService.readFileById(id);
            FileUploadResponse fileInfo = fileStorageService.getFileById(id);
            ByteArrayResource resource = new ByteArrayResource(fileContent);

            String contentType = determineContentType(fileInfo.getFileName());
            
            // For PDFs, we need to set specific headers to ensure proper browser handling
            if (contentType.equals("application/pdf")) {
                return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileInfo.getFileName() + "\"")
                    .header(HttpHeaders.CACHE_CONTROL, "public, max-age=3600")
                    .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                    .contentLength(fileContent.length)
                    .body(resource);
            }

            // For other file types, use the existing response
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
    public ResponseEntity<FileUploadResponse> updateFile(Long id, MultipartFile file, String uploadedBy, String language) {
        try {
            FileUploadResponse response = fileStorageService.updateFile(id, file, uploadedBy, language);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> deleteFile(Long id) {
        try {
            boolean deleted = fileStorageService.deleteFile(id);
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
            case "txt" -> "text/plain";
            case "csv" -> "text/csv";
            case "xlsx" -> "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            default -> "application/octet-stream";
        };
    }
}
