package com.usa.centimapa.event;


public interface EventService {
    Event findOne(long id);
    Event save(Event event) throws Exception;
    void removeEvent(long id);
}
