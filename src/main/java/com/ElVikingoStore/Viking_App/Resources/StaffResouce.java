package com.ElVikingoStore.Viking_App.Resources;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ElVikingoStore.Viking_App.Models.Staff;
import com.ElVikingoStore.Viking_App.Services.StaffService;

@RestController
@RequestMapping("/staff")
public class StaffResouce {
    @Autowired
    StaffService staffService;

    @GetMapping("/search")
    public ResponseEntity<Object> searchStaff(@RequestParam(required = false) Long id, @RequestParam(required = false) String name, @RequestParam(required = false) String query) {
        try {
            if (query == null) {
                return ResponseEntity.badRequest().body("Query parameter is required");
            }
    
            System.out.println("Query: " + query); // Imprimir la query recibida
    
            switch (query.toLowerCase()) {
                case "all" -> {
                    System.out.println("Searching all staff");
                    List<Staff> allStaff = staffService.getAll();
                    return ResponseEntity.ok(allStaff);
                }
                case "by-id" -> {
                    if (id == null) {
                        return ResponseEntity.badRequest().body("ID is required for 'by-id' query");
                    }
                    System.out.println("Searching staff by ID");
                    Staff staffById = staffService.getStaffById(id);
                    return ResponseEntity.ok(staffById);
                }
                case "by-email" -> {
                    if (name == null) {
                        return ResponseEntity.badRequest().body("Name is required for 'by-name' query");
                    }
                    System.out.println("Searching staff by Name");
                    Staff staffByEmail = staffService.getStaffByEmail(name);
                    return ResponseEntity.ok(staffByEmail);
                }
                
                default -> {
                    System.out.println("Invalid query parameter");
                    return ResponseEntity.badRequest().body("Invalid query parameter");
                }
            }
        } catch (NoSuchElementException | NumberFormatException e) {
            System.out.println("Error processing request: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error processing request: " + e.getMessage());
        }
    }
    
    @PostMapping("/save")
    public ResponseEntity<?> saveStaffInstance(@RequestBody Staff staff){
        if(staff.getName() == null || staff.getRole() == null || staff.getPhoneNumber() == null){
            return ResponseEntity.badRequest().body("Name, job, and phone number are required");
        }
        return ResponseEntity.ok(staffService.saveStaffInstance(staff));
    }
}
