package com.sadds.service;

import com.sadds.dto.OddsApiEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduledPollingService {

    private final OddsApiClient oddsApiClient;
    private final OddsMappingService oddsMappingService;

    @Scheduled(fixedRate = 21600000)
    public void pollOddsAndUpdate() {
        String[] sports = {"basketball_nba", "americanfootball_nfl"};
        for (String sport : sports) {
            OddsApiEventDto[] latestEvents = oddsApiClient.fetchOdds(sport);
            oddsMappingService.mapAndSaveOdds(latestEvents);
        }
    }

}
