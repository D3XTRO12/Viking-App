package VikingoLab.VikingApp.app.Services;

import java.util.List;
import static java.util.Objects.requireNonNull;

import org.springframework.stereotype.Service;

import VikingoLab.VikingApp.app.Models.Client;
import VikingoLab.VikingApp.app.Models.WorkOrder;
import VikingoLab.VikingApp.app.Repositories.ClientRepo;
import VikingoLab.VikingApp.app.Repositories.DeviceRepo;
import VikingoLab.VikingApp.app.Repositories.StaffRepo;
import VikingoLab.VikingApp.app.Repositories.WorkOrderRepo;
import jakarta.persistence.EntityNotFoundException;

@Service
public class WorkOrderService {

    private final WorkOrderRepo workOrderRepo;
    private final ClientRepo clientRepo;
    private final StaffRepo staffRepo;
    private final DeviceRepo deviceRepo;

    //@Autowired
    public WorkOrderService(WorkOrderRepo workOrderRepo, ClientRepo clientRepo, StaffRepo staffRepo, DeviceRepo deviceRepo) {
        this.workOrderRepo = workOrderRepo;
        this.clientRepo = clientRepo;
        this.staffRepo = staffRepo;
        this.deviceRepo = deviceRepo;
    }

    public List<WorkOrder> getAll() {
        return workOrderRepo.findAll();
    }

    public WorkOrder getWorkOrderById(Long id) {
        return workOrderRepo.findById(id).orElse(null);
    }

    public List<WorkOrder> findByClientDni(Integer clientDni) {
        return workOrderRepo.findByClient_Dni(clientDni);
    }

    public WorkOrder createWorkOrder(WorkOrder workOrderDto) throws CustomException {
        Client client = clientRepo.findByDni(requireNonNull(workOrderDto.getClient()).getDni());
        if (client == null) {
            client = new Client();
            client.setDni(requireNonNull(workOrderDto.getClient()).getDni());
            // Setear otros atributos del cliente si es necesario
            client = clientRepo.save(client);
        }
    
        WorkOrder workOrder = new WorkOrder();
        workOrder.setClient(client);
    
        try {
            workOrder.setStaff(staffRepo.findById(requireNonNull(workOrderDto.getStaff()).getId())
                    .orElseThrow(() -> new EntityNotFoundException("Staff not found")));
        } catch (EntityNotFoundException e) {
            System.out.println("Warning: Staff not found. Creating WorkOrder without Staff.");
            throw new CustomException("Staff not found. Please select an existing staff.", e);
        }
    
        try {
            workOrder.setDeviceId(deviceRepo.findById(requireNonNull(workOrderDto.getDeviceId()).getId())
                    .orElseThrow(() -> new EntityNotFoundException("Device not found")));
        } catch (EntityNotFoundException e) {
            System.out.println("Warning: Device not found. Creating WorkOrder without Device.");
            throw new CustomException("Device not found. Please select an existing device.", e);
        }
    
        workOrder.setIssueDescription(requireNonNull(workOrderDto.getIssueDescription()));
        workOrder.setRepairStatus(requireNonNull(workOrderDto.getRepairStatus()));
    
        return workOrderRepo.save(workOrder);
    }
    public class CustomException extends Exception {
        public CustomException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
}
