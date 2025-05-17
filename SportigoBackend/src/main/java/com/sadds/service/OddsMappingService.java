package com.sadds.service;

import com.sadds.dto.OddsApiBookmakerDto;
import com.sadds.dto.OddsApiEventDto;
import com.sadds.dto.OddsApiMarketDto;
import com.sadds.dto.OddsApiOutcomeDto;
import com.sadds.model.*;
import com.sadds.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OddsMappingService {

    private final EventRepository eventRepository;
    private final TeamRepository teamRepository;
    private final BookmakerRepository bookmakerRepository;
    private final MarketRepository marketRepository;
    private final SelectionRepository selectionRepository;
    private final LeagueRepository leagueRepository;

    public Team findOrCreateTeam(String teamName, String sportKey) {
        return teamRepository
                .findByNameAndSportKey(teamName, sportKey)
                .orElseGet(() -> {
                    Team team = new Team();
                    team.setName(teamName);
                    team.setSportKey(sportKey);
                    return teamRepository.save(team);
                });
    }

    @Transactional
    public void mapAndSaveOdds(OddsApiEventDto[] events) {
        for (OddsApiEventDto eventDto : events) {
            League league = leagueRepository
                    .findByLeagueKey(eventDto.sport_key())
                    .orElseGet(() -> {
                        League newLeague = League
                                .builder()
                                .leagueKey(eventDto.sport_key())
                                .title(eventDto.sport_title())
                                .build();
                        return leagueRepository.save(newLeague);
                    });

            Event event = eventRepository
                    .findById(eventDto.id())
                    .orElseGet(() -> Event
                            .builder()
                            .id(eventDto.id())
                            .sportKey(eventDto.sport_key())
                            .sportTitle(eventDto.sport_title())
                            .startTime(Instant.parse(eventDto.commence_time()))
                            .league(league)
                            .build());
            event = eventRepository.save(event);

            // Setting home and away team
            Team homeTeam = findOrCreateTeam(eventDto.home_team(), eventDto.sport_key());
            Team awayTeam = findOrCreateTeam(eventDto.away_team(), eventDto.sport_key());

            event.setHomeTeam(homeTeam);
            event.setAwayTeam(awayTeam);

            homeTeam.getHomeEvents().add(event);
            awayTeam.getAwayEvents().add(event);
            teamRepository.save(homeTeam);
            teamRepository.save(awayTeam);

            // saving bookmakers
            for (OddsApiBookmakerDto bookmakerDto : eventDto.bookmakers()) {
                Bookmaker bookmaker = bookmakerRepository
                        .findById(bookmakerDto.key())
                        .orElseGet(() -> Bookmaker
                                .builder()
                                .bookmakerKey(bookmakerDto.key())
                                .title(bookmakerDto.title())
                                .lastUpdate(bookmakerDto.last_update())
                                .build()
                        );
                bookmaker.setTitle(bookmakerDto.title());
                bookmaker.setLastUpdate(bookmakerDto.last_update());
                bookmakerRepository.save(bookmaker);

                // saving markets
                for (OddsApiMarketDto marketDto: bookmakerDto.markets()) {
                    Market market = marketRepository
                            .findByMarketKeyAndBookmakerAndEvent(
                                    marketDto.key(), bookmaker, event
                            ).orElse(null);
                    if (market == null) {
                        market = Market
                                .builder()
                                .marketKey(marketDto.key())
                                .lastUpdate(Instant.parse(marketDto.last_update()))
                                .event(event)
                                .bookmaker(bookmaker)
                                .build();
                    } else {
                        market.setLastUpdate(Instant.parse(marketDto.last_update()));
                        market.setMarketKey(marketDto.key());
                    }
                    marketRepository.save(market);

                    // 2 way connection
                    if (event.getMarkets() != null && !event.getMarkets().contains(market)) {
                        event.getMarkets().add(market);
                    }
                    if (bookmaker.getMarkets() != null && !bookmaker.getMarkets().contains(market)) {
                        bookmaker.getMarkets().add(market);
                    }

                    // saving selection(outcome)
                    for (OddsApiOutcomeDto outcomeDto: marketDto.outcomes()) {
                        Selection selection = selectionRepository.findByNameAndMarket(
                                outcomeDto.name(), market
                        ).orElse(null);
                        if (selection == null) {
                            selection = Selection
                                    .builder()
                                    .name(outcomeDto.name())
                                    .price(outcomeDto.price())
                                    .point(outcomeDto.point())
                                    .market(market)
                                    .build();
                        } else {
                            selection.setPrice(outcomeDto.price());
                            selection.setPoint(outcomeDto.point());
                        }
                        selectionRepository.save(selection);
                        if (market.getSelections() != null && !market.getSelections().contains(selection)) {
                            market.getSelections().add(selection);
                        }
                    }
                }
            }
            eventRepository.save(event);
        }
    }

}









