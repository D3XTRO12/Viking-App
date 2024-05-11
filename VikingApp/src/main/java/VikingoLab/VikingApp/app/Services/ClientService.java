package VikingoLab.VikingApp.app.Services;

import java.util.ArrayList;
import java.util.NoSuchElementException;

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
}
