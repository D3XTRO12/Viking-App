package VikingoLab.VikingApp.app.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import VikingoLab.VikingApp.app.Models.ClientModel;

@Repository
public interface ClientRepo extends JpaRepository<ClientModel, Long>{

    
    
}
