package com.usa.centimapa.packages;

import com.usa.centimapa.event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/package")
public class PackageResource {

    @Autowired
    private PackageService service;

    @GetMapping(value="/findAll")
    public List<Package> findAll(){
        return service.findAll();
    }
}
