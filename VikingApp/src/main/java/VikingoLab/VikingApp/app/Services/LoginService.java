package VikingoLab.VikingApp.app.Services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VikingoLab.VikingApp.app.Models.Client;
import VikingoLab.VikingApp.app.Models.Staff;
import VikingoLab.VikingApp.app.Models.WorkOrder;
import VikingoLab.VikingApp.app.Repositories.ClientRepo;
import VikingoLab.VikingApp.app.Repositories.StaffRepo;
import VikingoLab.VikingApp.app.Repositories.WorkOrderRepo;

@Service
public class LoginService {

    @Autowired
    private StaffRepo staffRepository;

    @Autowired
    private ClientRepo clientRepository;

    @Autowired
    private WorkOrderRepo workOrderRepository;

    public Staff authenticateStaff(String name, String password) {
        Staff staff = staffRepository.findByName(name);
        if (staff!= null && staff.getPassword().equals(password)) {
            return staff;
        } else {
            return null; // Credenciales incorrectas
        }
    }

    public Client authenticateClient(WorkOrder workOrder) {
        int clientDni = workOrder.getClient().getDni();
        Client client = clientRepository.findByDni(clientDni);
        if (client != null) {
            WorkOrder foundWorkOrder = workOrderRepository.findById(workOrder.getId())
                    .orElseThrow(() -> new NoSuchElementException("WorkOrder not found with id: " + workOrder.getId()));
            if (foundWorkOrder.getClient().getDni() == client.getDni()) {
                return client;
            }
        }
        return null; // Autenticación fallida
    }
}
