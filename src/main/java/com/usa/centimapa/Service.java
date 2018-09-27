package com.usa.centimapa;

import com.usa.centimapa.event.Event;

import java.util.List;

public interface Service<T> {

    T findOne(long id);
    T save(T t) throws Exception;
    void remove(long id);
    List<T> findAll();


}
