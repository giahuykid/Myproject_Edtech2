package com.sixofrods.edtech.service;

import com.sixofrods.edtech.dto.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileStorageService {
    // Create
    FileUploadResponse storeFile(MultipartFile file, String uploadedBy, String languageName);

    // Read
    byte[] readFileById(Long id) throws IOException;
    List<FileUploadResponse> getAllFiles();
    List<FileUploadResponse> getFilesByLanguage(String languageName);
    FileUploadResponse getFileById(Long id);
    FileUploadResponse getFileByName(String fileName);

    // Update
    FileUploadResponse updateFile(Long id, MultipartFile file, String uploadedBy, String languageName);

    // Delete
    boolean deleteFile(Long id);

    // Utility
    boolean isValidFileType(String fileName);
}
