package com.ElVikingoStore.Viking_App.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ElVikingoStore.Viking_App.Models.Staff;

@Repository
public interface StaffRepo extends JpaRepository<Staff, Long>{

        public Staff findByEmail(String email);
        
    
}
