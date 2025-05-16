package com.sadds.repo;

import com.sadds.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByNameAndSportKey(String name, String sportKey);
}
