package com.usa.centimapa.event;


import java.util.List;

public interface EventService {
    Event findOne(long id);
    Event save(Event event);
    void removeEvent(long id);
    List<Event> findAll();
    List<Event> findByUserId(Long id);
    void delete(long id);
}
