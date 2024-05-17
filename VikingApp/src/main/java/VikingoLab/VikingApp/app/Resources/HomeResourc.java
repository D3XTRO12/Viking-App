package VikingoLab.VikingApp.app.Resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class HomeResource {

    @GetMapping("/")
    public String greeting() {
        return "If you see this message, Welcome to my api -- Status: Running";
    }
}
