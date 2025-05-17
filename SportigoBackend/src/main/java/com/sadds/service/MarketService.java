package com.sadds.service;

import com.sadds.exception.EventException;
import com.sadds.exception.MarketException;
import com.sadds.mapper.MarketMapper;
import com.sadds.model.Event;
import com.sadds.model.Market;
import com.sadds.repo.EventRepository;
import com.sadds.repo.MarketRepository;
import com.sadds.request.MarketResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketService {

    private final MarketRepository marketRepository;
    private final EventRepository eventRepository;
    private final MarketMapper marketMapper;

    public List<MarketResponse> getMarketsForEvent(String eventId, String marketKey, String bookmakerKey, String sortBy, String direction) throws EventException {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventException("Event not found with id: " + eventId));

        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        List<Market> markets = marketRepository
                .findByEventWithFilters(event, marketKey, bookmakerKey, sort);

        return markets.stream()
                .map(marketMapper::toMarketResponse)
                .toList();
    }

    public MarketResponse getMarketById(Long marketId) throws MarketException {
        Market market = marketRepository
                .findById(marketId)
                .orElseThrow(() -> new MarketException("Market not found with id: " + marketId));
        return marketMapper.toMarketResponse(market);
    }
}
















