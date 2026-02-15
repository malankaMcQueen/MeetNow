package com.example.meetnow.service.file;

import com.example.meetnow.service.model.FileSaveResponse;
import com.example.meetnow.service.repository.FileResourceRepository;
import com.example.meetnow.service.model.FileResource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final FileResourceRepository repository;

    private static final String UPLOAD_DIR = "uploads";

    public FileSaveResponse upload(MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // создаём папку если нет
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalName = file.getOriginalFilename();
        String extension = extractExtension(originalName);

        String storedName = UUID.randomUUID() + extension;
        Path filePath = uploadPath.resolve(storedName);

        // сохраняем файл
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        FileResource resource = FileResource.builder()
                .storedName(storedName)
                .contentType(file.getContentType())
                .size(file.getSize())
                .path(filePath.toString())
                .createdAt(LocalDateTime.now())
                .build();

        resource = repository.save(resource);

        FileSaveResponse response = new FileSaveResponse(resource.getId());
        return response;
    }

    private String extractExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }

    public FileResource getByInfoId(Long imageId) {
        return repository.findById(imageId).orElseThrow(() -> new RuntimeException("Image not found. Id = " + imageId));
    }

    public Resource loadAsResource(Long id) throws FileNotFoundException {

        FileResource file = repository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("File not found"));

        try {
            Path path = Paths.get(file.getPath()).toAbsolutePath().normalize();
            Resource resource = new UrlResource(path.toUri());

            if (!resource.exists()) {
                throw new FileNotFoundException("File not found on disk");
            }

            return resource;

        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file", e);
        }
    }
    public FileResource getFileMetadata(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));
    }
}
