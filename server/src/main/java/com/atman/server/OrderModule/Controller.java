package com.atman.server.OrderModule;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/username")
    public String getUsername(){
        return "Atman";
    }

}
