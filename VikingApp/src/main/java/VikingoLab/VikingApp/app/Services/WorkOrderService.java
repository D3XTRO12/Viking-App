package VikingoLab.VikingApp.app.Services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VikingoLab.VikingApp.app.Models.WorkOrder;
import VikingoLab.VikingApp.app.Models.Client;
import VikingoLab.VikingApp.app.Repositories.ClientRepo;
import VikingoLab.VikingApp.app.Repositories.DeviceRepo;
import VikingoLab.VikingApp.app.Repositories.StaffRepo;
import VikingoLab.VikingApp.app.Repositories.WorkOrderRepo;


@Service
public class WorkOrderService {
    @Autowired
    WorkOrderRepo workOrderRepo;
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private StaffRepo staffRepo;
    @Autowired
    private DeviceRepo deviceRepo;

    public ArrayList<WorkOrder> getAll(){
        return (ArrayList<WorkOrder>) workOrderRepo.findAll();
    }

    public WorkOrder getWorkOrderById(Long id){
        return workOrderRepo.findById(id).orElse(null);
    }

    public ArrayList<WorkOrder> findByClient(int clientDni){
        return workOrderRepo.findByClient_Dni(clientDni);
    }


    public WorkOrder createWorkOrder(WorkOrder workOrderDto) {
        // Buscar el cliente por DNI o guardarlo si no existe
        Client client = clientRepo.findByDni(workOrderDto.getClient().getDni());
        if (client == null) {
            client = new Client();
            client.setDni(workOrderDto.getClient().getDni());
            // Setear otros atributos del cliente si es necesario
            client = clientRepo.save(client);
        }
    
        // Crear el objeto WorkOrder
        WorkOrder workOrder = new WorkOrder();
        workOrder.setClient(client);
        workOrder.setStaff(staffRepo.findById(workOrderDto.getStaff().getId()).orElse(null));
        workOrder.setDeviceId(deviceRepo.findById(workOrderDto.getDeviceId().getId()).orElse(null));
        workOrder.setIssueDescription(workOrderDto.getIssueDescription());
        workOrder.setRepairStatus(workOrderDto.getRepairStatus());
        workOrder.setPhotos(workOrderDto.getPhotos());
        workOrder.setVideos(workOrderDto.getVideos());
        workOrder.setNotes(workOrderDto.getNotes());
    
        // Guardar el WorkOrder
        return workOrderRepo.save(workOrder);
    }
    
}
