package com.sadds.mapper;

import com.sadds.model.Bet;
import com.sadds.request.BetResponse;
import org.springframework.stereotype.Service;

@Service
public class BetMapper {

    public BetResponse toBetResponse(Bet bet) {
        return BetResponse
                .builder()
                .id(bet.getId())
                .userId(bet.getUser().toString())
                .selectionId(bet.getSelection().getId())
                .selectionName(bet.getSelection().getName())
                .amount(bet.getAmount())
                .unmatchedAmount(bet.getUnmatchedAmount())
                .odds(bet.getOdds())
                .betType(bet.getBetType())
                .status(bet.getStatus())
                .placedAt(bet.getPlacedAt())
                .build();
    }
}
