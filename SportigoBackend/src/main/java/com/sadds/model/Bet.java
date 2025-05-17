package com.sadds.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Selection selection;

    @Positive
    private BigDecimal amount;

    @Positive
    private BigDecimal unmatchedAmount;

    private BigDecimal odds;
    private BetType betType;
    private BetStatus status;
    private Instant placedAt;

    public void reduceUnmatchedAmount(BigDecimal matchAmount) {
        this.unmatchedAmount = this.unmatchedAmount.subtract(matchAmount);
        if (this.unmatchedAmount.compareTo(BigDecimal.ZERO) == 0) {
            this.status = BetStatus.MATCHED;
        }
    }

}
