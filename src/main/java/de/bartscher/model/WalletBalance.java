package de.bartscher.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class WalletBalance {
    @Id
    @GeneratedValue
    private Long id;

    private float amount;
    private float worth;
    private WalletBalanceType type;
    @CreationTimestamp
    private Instant createdTimestamp;
}
