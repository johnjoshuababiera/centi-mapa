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
        return repository.findById(id).get();
    }

    @Override
    public Event save(Event event) throws Exception {
        if(isVacantDate(event.getDate(), event.getId())){
            return repository.save(event);
        }else{
            throw new Exception("Date is already booked!");
        }
    }

    @Override
    public void remove(long id) {
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
