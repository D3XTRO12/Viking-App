package VikingoLab.VikingApp.app.Services;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VikingoLab.VikingApp.app.Models.Client;
import VikingoLab.VikingApp.app.Repositories.ClientRepo;
import jakarta.transaction.Transactional;

@Service
public class ClientService {
    @Autowired
    ClientRepo clientRepo;
    
    public ArrayList<Client> getAll(){
        return (ArrayList<Client>) clientRepo.findAll();
    }

    public Client getClientById(Long id){
        return clientRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Client not found with id: " + id));
    }


    public Client getClientByDni(int dni){
        return clientRepo.findByDni(dni);
    }

    @Transactional
    public Client saveClientInstance(Client client){
        System.out.println("Datos recibidos para guardar: " + client.toString());
        return clientRepo.save(client);
    }
    public Client updateClient(Client existingClient, Client newClientDetails) throws Exception {
        // Aquí puedes realizar la lógica para actualizar el cliente existente con los nuevos detalles
        // Por ejemplo:
        existingClient.setName(newClientDetails.getName());
        existingClient.setAddress(newClientDetails.getAddress());
        existingClient.setPhoneNumber(newClientDetails.getPhoneNumber());
        existingClient.setSecondaryPhoneNumber(newClientDetails.getSecondaryPhoneNumber());
    
        // Guardar los cambios en la base de datos
        // Esto dependerá de cómo esté configurada tu persistencia (JPA, Hibernate, etc.)
        // Por ejemplo, usando JPA/Hibernate:
        clientRepo.save(existingClient);
    
        return existingClient;
    }
    public boolean deleteClient(Long id) {
        try {
            // Buscar el cliente por id
            Optional<Client> optionalClient = clientRepo.findById(id);
            if (optionalClient.isEmpty()) {
                return false; // Cliente no encontrado
            }
    
            // Eliminar el cliente
            clientRepo.deleteById(id);
    
            return true; // Cliente eliminado exitosamente
        } catch (Exception e) {
            // Manejar errores generales
            return false; // Error al eliminar
        }
    }
    
    
}
