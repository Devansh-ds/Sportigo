package com.sadds.repo;

import com.sadds.model.Bookmaker;
import com.sadds.model.Event;
import com.sadds.model.Market;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarketRepository extends JpaRepository<Market, Long> {
    Optional<Market> findByMarketKeyAndBookmakerAndEvent(String key, Bookmaker bookmaker, Event event);

}
