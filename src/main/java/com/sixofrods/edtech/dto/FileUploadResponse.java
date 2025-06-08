package com.sixofrods.edtech.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponse {
    private Long id;
    private String fileName;
    private String fileType;
    private long size;
    private String language;
    private String uploadedBy;
    private String uploadDate;
    private String uploadStatus;
    private String message;
}
