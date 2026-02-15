package com.example.meetnow.service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "file_resource")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stored_name", nullable = false)
    private String storedName;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "size")
    private Long size;

    @Column(name = "path")
    private String path;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
