package VikingoLab.VikingApp.app.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import VikingoLab.VikingApp.app.Models.Staff;

@Repository
public interface StaffRepo extends JpaRepository<Staff, Long>{

    
    Staff findByName(String name);
        
    
}
