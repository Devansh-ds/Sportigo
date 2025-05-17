package com.sadds.controller;

import com.sadds.exception.EventException;
import com.sadds.request.EventResponse;
import com.sadds.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<Page<EventResponse>> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startTime") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) String sportKey,
            @RequestParam(required = false) String sportTitle,
            @RequestParam(required = false) String teamName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startAfter,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startBefore
    ) {
        return ResponseEntity.ok(eventService.getAllEvents(
                page, size, sortBy, direction, sportKey, sportTitle, teamName, startAfter, startBefore
        ));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable String eventId) throws EventException {
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }

}
