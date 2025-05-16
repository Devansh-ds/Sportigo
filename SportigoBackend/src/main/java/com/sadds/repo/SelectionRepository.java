package com.sadds.repo;

import com.sadds.model.Market;
import com.sadds.model.Selection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SelectionRepository extends JpaRepository<Selection, Long> {
    Optional<Selection> findByNameAndMarket(String name, Market market);

}
