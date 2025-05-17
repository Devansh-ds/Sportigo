package com.sadds.repo;

import com.sadds.model.Bet;
import com.sadds.model.BetStatus;
import com.sadds.model.BetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BetRepository extends JpaRepository<Bet, Long> {

    // Get all bets for a given event
    @Query("""
        SELECT b FROM Bet b
        WHERE b.selection.market.event.id = :eventId
    """)
    List<Bet> findByEventId(String eventId);

    // Get all bets placed by a specific user
    List<Bet> findByUserId(Integer userId);

    // Find opposite open bets for matching (same selection, same odds, opposite type)
    @Query("""
        SELECT b FROM Bet b
        WHERE b.status = :status
        AND b.selection.id = :selectionId
        AND b.betType = :oppositeType
        AND b.odds = :odds
    """)
    List<Bet> findOppositeOpenBets(Long selectionId, BetType oppositeType,
                                   BigDecimal odds, BetStatus status);
}
