package de.bartscher.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CryptoWallet {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true, name = "wallet_identifier")
    @JsonProperty("wallet_id")
    private String walletId;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Coin coin;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<WalletBalance> balanceHistory;

    @CreationTimestamp
    private Instant createdTimestamp;

    @UpdateTimestamp
    private Instant updatedTimestamp;
}
