package com.sadds.repo;

import com.sadds.model.League;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeagueRepository extends JpaRepository<League, Long> {
    Optional<League> findByLeagueKey(String leagueKey);
}
