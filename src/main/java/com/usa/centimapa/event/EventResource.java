package com.usa.centimapa.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/event")
public class EventResource {
    
    @Autowired
    private EventService service;

    @PostMapping("/save")
    public Event create(@RequestBody Event event) throws Exception {
        return service.save(event);
    }

    @GetMapping(value="/findById")
    public Event findStudent(@RequestParam long id){
        return service.findOne(id);
    }

    @DeleteMapping(value="/remove")
    public void remove(@PathVariable long id){
        service.removeEvent(id);
    }
}
