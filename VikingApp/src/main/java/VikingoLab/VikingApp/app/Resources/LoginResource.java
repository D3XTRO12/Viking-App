package VikingoLab.VikingApp.app.Resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import VikingoLab.VikingApp.app.Models.Client;
import VikingoLab.VikingApp.app.Models.Staff;
import VikingoLab.VikingApp.app.Models.WorkOrder;
import VikingoLab.VikingApp.app.Services.LoginService;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginResource {

    @Autowired
    private LoginService loginService;

    @PostMapping("/staff")
    public ResponseEntity<Staff> loginStaff(@RequestBody Map<String, Object> requestBody) {
        String name = (String) requestBody.get("name");
        String password = (String) requestBody.get("password");

        Staff authenticatedStaff = loginService.authenticateStaff(name, password);
        if (authenticatedStaff!= null) {
            return new ResponseEntity<>(authenticatedStaff, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/client")
    public ResponseEntity<Client> authenticateClient(@RequestBody WorkOrder workOrder) {
        Client client = loginService.authenticateClient(workOrder);
        if (client!= null) {
            return new ResponseEntity<>(client, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
