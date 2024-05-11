package VikingoLab.VikingApp.app.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import VikingoLab.VikingApp.app.Models.Device;

@Repository
public interface DeviceRepo extends JpaRepository<Device, Long> {

    Device findBySerialNumber(String serialNumber);
  
} 
