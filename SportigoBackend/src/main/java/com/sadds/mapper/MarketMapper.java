package com.sadds.mapper;

import com.sadds.model.Market;
import com.sadds.model.Selection;
import com.sadds.request.MarketResponse;
import com.sadds.request.SelectionResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarketMapper {

    public MarketResponse toMarketResponse(Market market) {

        List<SelectionResponse> selections = market.getSelections().stream()
                .map(this::toSelectionResponse)
                .toList();

        MarketResponse response = MarketResponse
                .builder()
                .id(market.getId())
                .marketKey(market.getMarketKey())
                .lastUpdate(market.getLastUpdate())
                .selections(selections)
                .bookmakerTitle(market.getBookmaker().getTitle())
                .build();
        return response;
    }

    private SelectionResponse toSelectionResponse(Selection selection) {
        return SelectionResponse
                .builder()
                .id(selection.getId())
                .name(selection.getName())
                .price(selection.getPrice())
                .point(selection.getPoint())
                .build();
    }
}
