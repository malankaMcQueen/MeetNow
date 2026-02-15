package com.example.meetnow.api;

import com.example.meetnow.service.file.FileStorageService;
import com.example.meetnow.service.model.FileResource;
import com.example.meetnow.service.model.FileSaveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class FileController {

    private final FileStorageService storageService;

    @PostMapping("/upload")
    public FileSaveResponse upload(@RequestParam("file") MultipartFile file) throws Exception {

        FileSaveResponse saved = storageService.upload(file);

        return saved;
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable Long id) throws FileNotFoundException {

        FileResource metadata = storageService.getFileMetadata(id);
        Resource resource = storageService.loadAsResource(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(metadata.getContentType()))
                .body(resource);
    }
}
