package VikingoLab.VikingApp.app.Services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VikingoLab.VikingApp.app.Models.Staff;
import VikingoLab.VikingApp.app.Repositories.StaffRepo;
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
    public Staff getStaffByName(String name){
        return staffRepo.findByName(name);
    }

    @Transactional
    public Staff saveStaffInstance(Staff staff){
        System.out.println("Datos recibidos para guardar: " + staff.toString());
        return staffRepo.save(staff);
    }
}
