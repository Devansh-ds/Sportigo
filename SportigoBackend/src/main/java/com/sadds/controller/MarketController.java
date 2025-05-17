package com.sadds.controller;

import com.sadds.exception.EventException;
import com.sadds.exception.MarketException;
import com.sadds.request.MarketResponse;
import com.sadds.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class MarketController {

    private final MarketService marketService;

    @GetMapping("/{eventId}/markets")
    public ResponseEntity<List<MarketResponse>> getMarketsForEvent(
            @PathVariable String eventId,
            @RequestParam(required = false) String marketKey,
            @RequestParam(required = false) String bookmakerKey,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) throws EventException {
        List<MarketResponse> markets = marketService.getMarketsForEvent(
                eventId, marketKey, bookmakerKey, sortBy, direction
        );
        return ResponseEntity.ok(markets);
    }

    @GetMapping("/market/{marketId}")
    public ResponseEntity<MarketResponse> getMarketByMarketId(@PathVariable Long marketId) throws MarketException {
        return ResponseEntity.ok(marketService.getMarketById(marketId));
    }

}
