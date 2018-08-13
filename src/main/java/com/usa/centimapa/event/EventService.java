package com.usa.centimapa.event;


import java.util.List;

public interface EventService {
    Event findOne(long id);
    Event save(Event event) throws Exception;
    void removeEvent(long id);
    List<Event> findAll();
}
