package com.ElVikingoStore.Viking_App.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ElVikingoStore.Viking_App.Models.Device;
import com.ElVikingoStore.Viking_App.Repositories.DeviceRepo;

import jakarta.transaction.Transactional;

@Service
public class DeviceService {
    @Autowired
    DeviceRepo deviceRepo;

    public ArrayList<Device> getAll(){
        return (ArrayList<Device>) deviceRepo.findAll();
    }

    public Device getDeviceById(Long id){
        return deviceRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Device not found with id: " + id));
    }

    public Device getDeviceBySerialNumber(String serialNumber){
        return deviceRepo.findBySerialNumber(serialNumber);
    }
    public List<Device> getDevicesByBrand(String brand) {
        return deviceRepo.findByBrand(brand); // Asegúrate de que este método esté definido en tu repositorio
    }
    @Transactional
    public Device saveDeviceInstance(Device device){
        System.out.println("Datos recibidos para guardar: " + device.toString());
        return deviceRepo.save(device);
    }
}
