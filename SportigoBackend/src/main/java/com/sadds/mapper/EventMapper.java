package com.sadds.mapper;

import com.sadds.exception.TeamException;
import com.sadds.model.Event;
import com.sadds.model.Team;
import com.sadds.repo.TeamRepository;
import com.sadds.request.EventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventMapper {

    private final TeamRepository teamRepository;

    public EventResponse toEventResponse(Event event) {

        Team homeTeam = null;
        Team awayTeam = null;
        try {
            homeTeam = teamRepository
                    .findById(event.getHomeTeam().getId())
                    .orElseThrow(() -> new TeamException("Home Team does not exist with event id: " + event.getId()));
            awayTeam = teamRepository
                    .findById(event.getAwayTeam().getId())
                    .orElseThrow(() -> new TeamException("Away Team does not exist with event id: " + event.getId()));
        } catch (TeamException e) {
            throw new RuntimeException(e);
        }

        return EventResponse
                .builder()
                .id(event.getId())
                .sportKey(event.getSportKey())
                .sportTitle(event.getSportTitle())
                .startTime(event.getStartTime())
                .awayTeamName(awayTeam.getName())
                .homeTeamName(homeTeam.getName())
                .build();
    }
}
