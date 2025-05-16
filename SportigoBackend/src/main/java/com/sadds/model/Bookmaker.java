package com.sadds.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Bookmaker {

    @Id
    @Column(name = "bookmaker_key")
    private String bookmakerKey;
    private String title;

    private String lastUpdate;

    @OneToMany(mappedBy = "bookmaker")
    private List<Market> markets = new ArrayList<>();

}
