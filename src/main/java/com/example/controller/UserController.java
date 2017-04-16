package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.config.PersonConfig;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private PersonConfig pc;
    
    @RequestMapping("/{name}")
    public String getPerson(@PathVariable String name){
        pc.setName(name);
        return pc.toString();
    }
    
}
