package com.ElVikingoStore.Viking_App.Services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ElVikingoStore.Viking_App.Models.Staff;
import com.ElVikingoStore.Viking_App.Repositories.StaffRepo;

import jakarta.transaction.Transactional;

@Service
public class StaffService {
    @Autowired
    StaffRepo staffRepo;

    public ArrayList<Staff> getAll(){
        return (ArrayList<Staff>) staffRepo.findAll();
    }

    public Staff getStaffById(Long id){
        return staffRepo.findById(id).get();

    }
    public Staff getStaffByEmail(String email){
        return staffRepo.findByEmail(email);
    }

    @Transactional
    public Staff saveStaffInstance(Staff staff){
        System.out.println("Datos recibidos para guardar: " + staff.toString());
        return staffRepo.save(staff);
    }
}
