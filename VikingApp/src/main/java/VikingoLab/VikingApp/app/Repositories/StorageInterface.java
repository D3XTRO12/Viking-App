package VikingoLab.VikingApp.app.Repositories;

import org.springframework.web.multipart.MultipartFile;


public interface StorageInterface {
    void init();

    String store(MultipartFile file);
    
    org.springframework.core.io.Resource loadResource(String filename);
}
