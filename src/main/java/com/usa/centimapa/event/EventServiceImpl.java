package com.usa.centimapa.event;

import com.usa.centimapa.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService{

    @Autowired
    private EventRepository repository;

    @Override
    public Event findOne(long id) {
        return repository.getOne(id);
    }

    @Override
    public Event save(Event event) throws Exception {
        if(isVacantDate(event.getDate(), null)){
            return repository.save(event);
        }
        return null;
    }

    @Override
    public void removeEvent(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Event> findAll() {
        return repository.findAll();
    }

    boolean isVacantDate(long date, Long id){
        Event event  = repository.findByDateBetween(DateTimeUtil.getStartOfDAy(date), DateTimeUtil.getEndOfDay(date));
        return event ==null || (event!=null && id==event.getId()) ;
    }
}
