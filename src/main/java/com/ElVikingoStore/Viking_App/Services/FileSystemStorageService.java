package com.ElVikingoStore.Viking_App.Services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ElVikingoStore.Viking_App.Repositories.StorageInterface;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public final class FileSystemStorageService implements StorageInterface {

    private final Path rootLocation;

    // Inyectar la propiedad upload.path
    public FileSystemStorageService(@Value("${upload.path}") String uploadPath) {
        this.rootLocation = Paths.get(uploadPath);
        init(); // Inicializar el directorio si no existe
    }

    @Override
    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }
            Path destinationFile = this.rootLocation.resolve(
                    Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();

            // Asegurarse de que el archivo no esté fuera del directorio objetivo
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new RuntimeException("Cannot store file outside current directory.");
            }

            // Copiar el archivo
            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
            log.info("Archivo guardado: " + destinationFile.toString()); // Log para verificar el archivo guardado
            return file.getOriginalFilename();
        } catch (IOException | RuntimeException e) {
            log.error("Error al guardar el archivo: " + e.getMessage());
            throw new RuntimeException("Failed to store file. Error: " + e.getMessage());
        }
    }

    @Override
    @PostConstruct
    public void init() {
        try {
            // Crear el directorio si no existe
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
                log.info("Directorio creado: " + rootLocation.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage: " + e.getMessage());
        }
    }

    @Override
    public Resource loadResource(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
