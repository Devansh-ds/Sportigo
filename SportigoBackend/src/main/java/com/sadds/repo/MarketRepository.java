package com.sadds.repo;

import com.sadds.model.Bookmaker;
import com.sadds.model.Event;
import com.sadds.model.Market;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MarketRepository extends JpaRepository<Market, Long> {

    Optional<Market> findByMarketKeyAndBookmakerAndEvent(String key, Bookmaker bookmaker, Event event);

    @Query("SELECT m FROM Market m " +
            "WHERE m.event = :event " +
            "AND (:marketKey IS NULL OR m.marketKey = :marketKey) " +
            "AND (:bookmakerKey IS NULL OR m.bookmaker.bookmakerKey = :bookmakerKey)")
    List<Market> findByEventWithFilters(
            @Param("event") Event event,
            @Param("marketKey") String marketKey,
            @Param("bookmakerKey") String bookmakerKey,
            Sort sort
    );
}
