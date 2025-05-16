package com.sadds.repo;

import com.sadds.model.Bookmaker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmakerRepository extends JpaRepository<Bookmaker, String> {
}
