package com.sadds.repo;

import com.sadds.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, String> {
}
