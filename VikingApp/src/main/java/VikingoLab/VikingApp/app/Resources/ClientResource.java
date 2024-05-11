package VikingoLab.VikingApp.app.Resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

import VikingoLab.VikingApp.app.Models.Client;
import VikingoLab.VikingApp.app.Services.ClientService;

@RestController
@RequestMapping("/client")
public class ClientResource {

    @Autowired
    ClientService clientService;

    @GetMapping("/search")
    public ResponseEntity<Object> searchClient(@RequestParam(required = false) Integer id, @RequestParam(required = false) String dni, @RequestParam(required = false) String query) {
        try {
            if (query == null) {
                return ResponseEntity.badRequest().body("Query parameter is required");
            }
    
            System.out.println("Query: " + query); // Imprimir la query recibida
    
            switch (query.toLowerCase()) {
                case "all":
                    System.out.println("Searching all clients");
                    List<Client> clients = clientService.getAll();
                    return ResponseEntity.ok(clients);
                case "by-id":
                    if (id == null) {
                        return ResponseEntity.badRequest().body("ID is required for 'by-id' query");
                    }
                    System.out.println("Searching client by ID");
                    Client clientById = clientService.getClientById(id.longValue());
                    return ResponseEntity.ok(clientById);
                case "by-dni":
                    if (dni == null) {
                        return ResponseEntity.badRequest().body("DNI is required for 'by-dni' query");
                    }
                    System.out.println("Searching client by DNI");
                    Client clientByDni = clientService.getClientByDni(Integer.parseInt(dni));
                    return ResponseEntity.ok(clientByDni);
                default:
                    System.out.println("Invalid query parameter");
                    return ResponseEntity.badRequest().body("Invalid query parameter");
            }
        } catch (NoSuchElementException | NumberFormatException e) {
            System.out.println("Error processing request: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error processing request: " + e.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveClientInstance(@RequestBody Client client){
        if(client.getName() == null || client.getAddress() == null || client.getPhoneNumber() == null){
            return ResponseEntity.badRequest().body("Name, address, and phone number are required");
        }
        return ResponseEntity.ok(clientService.saveClientInstance(client));
    }
}
