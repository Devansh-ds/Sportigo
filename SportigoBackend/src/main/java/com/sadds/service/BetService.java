package com.sadds.service;

import com.sadds.exception.BadCredentialsException;
import com.sadds.exception.BetException;
import com.sadds.exception.UserException;
import com.sadds.mapper.BetMapper;
import com.sadds.model.*;
import com.sadds.repo.BetRepository;
import com.sadds.repo.MarketRepository;
import com.sadds.repo.SelectionRepository;
import com.sadds.request.BetRequest;
import com.sadds.request.BetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BetService {

    private final BetRepository betRepository;
    private final UserService userService;
    private final MarketRepository marketRepository;
    private final SelectionRepository selectionRepository;
    private final BetMapper betMapper;

    @Transactional
    public BetResponse placeBet(BetRequest request, String token)
            throws UserException, BadCredentialsException, BetException {

        User user = userService.findByJwtToken(token);

        Selection selection = selectionRepository.findById(request.selectionId())
                .orElseThrow(() -> new BetException("Invalid selection ID: " + request.selectionId()));

        Bet bet = Bet.builder()
                .user(user)
                .selection(selection)
                .amount(request.amount())
                .unmatchedAmount(request.amount())
                .odds(request.odds())
                .betType(request.type())
                .status(BetStatus.OPEN)
                .placedAt(Instant.now())
                .build();

        Bet savedBet = betRepository.save(bet);

        matchBets(savedBet);

        return betMapper.toBetResponse(savedBet);
    }

    private void matchBets(Bet newBet) {
        List<Bet> openOpposites = betRepository.findOppositeOpenBets(
                newBet.getSelection().getId(),
                newBet.getBetType() == BetType.BACK ? BetType.LAY : BetType.BACK,
                newBet.getOdds(),
                BetStatus.OPEN
        );

        for (Bet opposite : openOpposites) {
            if (newBet.getUnmatchedAmount().compareTo(BigDecimal.ZERO) <= 0) break;

            BigDecimal matchAmount = newBet.getUnmatchedAmount().min(opposite.getUnmatchedAmount());

            newBet.reduceUnmatchedAmount(matchAmount);
            opposite.reduceUnmatchedAmount(matchAmount);

            betRepository.save(opposite);
        }

        betRepository.save(newBet);
    }

    public List<BetResponse> getBetsForEvent(String eventId) throws BetException {
        List<Bet> bets = betRepository.findByEventId(eventId);
        if (bets.isEmpty()) {
            throw new BetException("No bets found for event ID: " + eventId);
        }
        return bets.stream()
                .map(betMapper::toBetResponse)
                .toList();
    }

    public List<BetResponse> getUsersBets(String token)
            throws UserException, BadCredentialsException, BetException {
        User user = userService.findByJwtToken(token);
        List<Bet> bets = betRepository.findByUserId(user.getId());
        if (bets.isEmpty()) {
            throw new BetException("No bets found for user ID: " + user.getId());
        }
        return bets.stream()
                .map(betMapper::toBetResponse)
                .toList();
    }
}
