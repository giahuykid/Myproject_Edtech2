package com.sixofrods.edtech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "files")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileType;

    @Column(nullable = false, length = 1000)
    private String filePath;

    private long size;

    @Column(nullable = false)
    private LocalDateTime uploadDate;

    @Column(nullable = false)
    private String uploadedBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "language_id", nullable = true)
    private Language language;
}
