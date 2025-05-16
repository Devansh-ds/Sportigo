package com.sadds.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "league_key")
    private String leagueKey;
    private String title;
    private String description;

    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events = new ArrayList<>();

}