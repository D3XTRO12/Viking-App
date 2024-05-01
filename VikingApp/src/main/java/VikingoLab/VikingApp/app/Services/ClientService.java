package VikingoLab.VikingApp.app.Services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VikingoLab.VikingApp.app.Models.ClientModel;
import VikingoLab.VikingApp.app.Repositories.ClientRepo;
import jakarta.transaction.Transactional;

@Service
public class ClientService {
    @Autowired
    ClientRepo clientRepo;

    public ArrayList<ClientModel> getAll(){
        return (ArrayList<ClientModel>)clientRepo.findAll();
    }
    
    @Transactional
    public ClientModel saveClientInstance(ClientModel client){
        return clientRepo.save(client);
    }

}