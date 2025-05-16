package com.sadds.repo;

import com.sadds.model.League;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<League, Long> {
}
