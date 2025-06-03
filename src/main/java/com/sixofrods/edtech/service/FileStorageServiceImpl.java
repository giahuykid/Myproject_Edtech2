package com.sixofrods.edtech.service;

import com.sixofrods.edtech.dto.FileUploadResponse;
import com.sixofrods.edtech.entity.FileEntity;
import com.sixofrods.edtech.entity.Language;
import com.sixofrods.edtech.repository.FileRepository;
import com.sixofrods.edtech.repository.LanguageRP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private LanguageRP languageRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final List<String> ALLOWED_EXTENSIONS = Arrays.asList(
            "pdf", "doc", "docx", "mp3"
    );

    private FileUploadResponse convertToFileUploadResponse(FileEntity entity) {
        return FileUploadResponse.builder()
                .fileName(entity.getFileName())
                .fileType(entity.getFileType())
                .size(entity.getSize())
                .uploadStatus("success")
                .message("File found")
                .build();
    }

    private Language getOrCreateLanguage(String languageName) {
        return languageRepository.findByLanguageName(languageName)
                .orElseGet(() -> {
                    Language newLanguage = Language.builder()
                            .languageName(languageName)
                            .build();
                    return languageRepository.save(newLanguage);
                });
    }

    @Override
    public FileUploadResponse storeFile(MultipartFile file, String uploadedBy, String languageName) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                throw new RuntimeException("Invalid file path sequence in filename: " + fileName);
            }

            if (!isValidFileType(fileName)) {
                return FileUploadResponse.builder()
                        .fileName(fileName)
                        .fileType(file.getContentType())
                        .size(file.getSize())
                        .uploadStatus("failed")
                        .message("Invalid file type. Only PDF, Word documents, and MP3 audio files are allowed.")
                        .build();
            }

            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
            Path targetLocation = uploadPath.resolve(uniqueFileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Language language = getOrCreateLanguage(languageName);

            FileEntity fileEntity = FileEntity.builder()
                    .fileName(fileName)
                    .fileType(file.getContentType())
                    .filePath(targetLocation.toString())
                    .size(file.getSize())
                    .uploadDate(LocalDateTime.now())
                    .uploadedBy(uploadedBy)
                    .language(language)
                    .build();

            fileRepository.save(fileEntity);

            return FileUploadResponse.builder()
                    .fileName(fileName)
                    .fileType(file.getContentType())
                    .size(file.getSize())
                    .uploadStatus("success")
                    .message("File uploaded successfully")
                    .build();

        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public byte[] readFile(String fileName) throws IOException {
        String decodedFileName = java.net.URLDecoder.decode(fileName, java.nio.charset.StandardCharsets.UTF_8);
        FileEntity fileEntity = fileRepository.findByFileName(decodedFileName)
                .orElseThrow(() -> new RuntimeException("File not found: " + decodedFileName));
        return Files.readAllBytes(Paths.get(fileEntity.getFilePath()));
    }

    @Override
    public List<FileUploadResponse> getAllFiles() {
        return fileRepository.findAll().stream()
                .map(this::convertToFileUploadResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<FileUploadResponse> getFilesByLanguage(String languageName) {
        return fileRepository.findByLanguage_LanguageName(languageName).stream()
                .map(this::convertToFileUploadResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FileUploadResponse getFileByName(String fileName) {
        String decodedFileName = java.net.URLDecoder.decode(fileName, java.nio.charset.StandardCharsets.UTF_8);
        String cleanFileName = StringUtils.cleanPath(decodedFileName.trim());
        
        FileEntity fileEntity = fileRepository.findByFileName(cleanFileName)
                .orElseThrow(() -> new RuntimeException("File not found: " + cleanFileName));
        
        return convertToFileUploadResponse(fileEntity);
    }

    @Override
    public FileUploadResponse updateFile(String fileName, MultipartFile file, String uploadedBy, String languageName) {
        FileEntity existingFile = fileRepository.findByFileName(fileName)
                .orElseThrow(() -> new RuntimeException("File not found: " + fileName));
        
        try {
            Files.deleteIfExists(Paths.get(existingFile.getFilePath()));
        } catch (IOException e) {
            throw new RuntimeException("Could not delete existing file: " + fileName, e);
        }

        return storeFile(file, uploadedBy, languageName);
    }

    @Override
    public boolean deleteFile(String fileName) {
        try {
            FileEntity fileEntity = fileRepository.findByFileName(fileName)
                    .orElseThrow(() -> new RuntimeException("File not found: " + fileName));

            Files.deleteIfExists(Paths.get(fileEntity.getFilePath()));
            fileRepository.delete(fileEntity);
            return true;
        } catch (Exception ex) {
            throw new RuntimeException("Could not delete file: " + fileName, ex);
        }
    }

    @Override
    public boolean isValidFileType(String fileName) {
        String fileExtension = getFileExtension(fileName);
        return ALLOWED_EXTENSIONS.contains(fileExtension.toLowerCase());
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
