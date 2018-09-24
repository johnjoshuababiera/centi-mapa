package com.usa.centimapa.event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByDateBetween(long from, long to);
    List<Event> findByUserId(Long id);
}
