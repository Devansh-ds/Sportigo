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
public class Selection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private Double point;

    @ManyToOne
    private Market market;

    @OneToMany(mappedBy = "selection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bet> bets = new ArrayList<>();

}
