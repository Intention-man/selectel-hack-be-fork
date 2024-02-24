package com.thatsgoodmoney.selectelhackbe.services;

import com.thatsgoodmoney.selectelhackbe.domain.entities.ImageEntity;
import com.thatsgoodmoney.selectelhackbe.repositories.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final String uploadDir = "./uploads";

    public Long storeFile(MultipartFile file) {
        try {
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);
            Path filePath = uploadPath.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath);

            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setTitle(file.getName());
            return imageRepository.save(imageEntity).getImageId();
        } catch (IOException ex) {
            throw new RuntimeException("Failed to store file: " + ex.getMessage());
        }
    }

    public ByteArrayResource loadFileAsResource(String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(filePath));
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found: " + filename);
            }
        } catch (IOException ex) {
            throw new RuntimeException("File not found: " + filename, ex);
        }
    }
}
