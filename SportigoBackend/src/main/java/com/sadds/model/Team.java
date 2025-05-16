package com.sadds.model;

import jakarta.persistence.*;
import lombok.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String sportKey;

    @OneToMany(mappedBy = "homeTeam")
    private List<Event> homeEvents = new ArrayList<>();

    @OneToMany(mappedBy = "awayTeam")
    private List<Event> awayEvents = new ArrayList<>();
}
