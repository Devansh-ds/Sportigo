package com.sadds.service;

import com.sadds.exception.EventException;
import com.sadds.mapper.EventMapper;
import com.sadds.model.Event;
import com.sadds.repo.EventRepository;
import com.sadds.request.EventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public Page<EventResponse> getAllEvents(int page, int size, String sortBy, String direction,
                                            String sportKey, String sportTitle, String teamName,
                                            Instant startAfter, Instant startBefore
    ) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Event> spec = Specification.where(null);

        if (sportKey != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("sportKey"), sportKey));
        }
        if (sportTitle != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("sportTitle"), sportTitle));
        }

        if (teamName != null) {
            spec = spec.and((root, query, cb) ->
                    cb.or(
                            cb.like(cb.lower(root.get("homeTeam").get("name")), "%" + teamName.toLowerCase() + "%"),
                            cb.like(cb.lower(root.get("awayTeam").get("name")), "%" + teamName.toLowerCase() + "%")
                    ));
        }
        if (startAfter != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("startTime"), startAfter));
        }
        if (startBefore != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("startTime"), startBefore));
        }

        Page<Event> events = eventRepository.findAll(spec, pageable);
        return events.map(eventMapper::toEventResponse);
    }

    public EventResponse getEventById(String eventId) throws EventException {
        Event event = eventRepository
                .findById(eventId)
                .orElseThrow(() -> new EventException("Event does not exist with id: " + eventId));
        return eventMapper.toEventResponse(event);
    }




}






















