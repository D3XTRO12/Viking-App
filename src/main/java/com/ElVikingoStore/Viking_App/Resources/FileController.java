package com.ElVikingoStore.Viking_App.Resources;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;

@RestController
public class FileController {

    @Value("${spring.web.resources.static-locations}")
    private String staticLocations;

    private Path rootLocation;

    // Usar el método init() para inicializar rootLocation
    @PostConstruct
    public void init() {
        // Eliminar "file:///" del principio, si es necesario
        String locationPath = staticLocations.replace("file:///", "");
        this.rootLocation = Paths.get(locationPath);
    }

    @GetMapping("/uploads/{filename:.+}")
    public Resource serveFile(@PathVariable String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (RuntimeException | MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
