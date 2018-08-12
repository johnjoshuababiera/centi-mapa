package com.usa.centimapa.event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    Event findByDateBetween(long from, long to);
}
