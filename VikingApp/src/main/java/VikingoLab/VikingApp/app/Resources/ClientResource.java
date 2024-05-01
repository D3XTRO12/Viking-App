package VikingoLab.VikingApp.app.Resources;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import VikingoLab.VikingApp.app.Models.ClientModel;
import VikingoLab.VikingApp.app.Services.ClientService;

@RestController
@RequestMapping("/client")
public class ClientResource {
    @Autowired
    ClientService clientService;

    @GetMapping()
    public ArrayList<ClientModel> getAll(){
        return clientService.getAll();
    }
    @PostMapping()
    public ClientModel saveClientInstance(@RequestBody ClientModel client){
        return clientService.saveClientInstance(client);
    }
    
      
}
