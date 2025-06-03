package com.sixofrods.edtech.service;

import com.sixofrods.edtech.dto.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileStorageService {
    // Create
    FileUploadResponse storeFile(MultipartFile file, String uploadedBy, String languageName);

    // Read
    byte[] readFile(String fileName) throws IOException;
    List<FileUploadResponse> getAllFiles();
    List<FileUploadResponse> getFilesByLanguage(String languageName);
    FileUploadResponse getFileByName(String fileName);

    // Update
    FileUploadResponse updateFile(String fileName, MultipartFile file, String uploadedBy, String languageName);

    // Delete
    boolean deleteFile(String fileName);

    // Utility
    boolean isValidFileType(String fileName);
}
