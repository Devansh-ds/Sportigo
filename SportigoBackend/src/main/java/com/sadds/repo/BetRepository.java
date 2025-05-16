package com.sadds.repo;

import com.sadds.model.Bet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BetRepository extends JpaRepository<Bet, Long> {
}
