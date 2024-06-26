package de.bartscher.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
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
public class Coin {

    private String name;
    private String abbreviation;
    @Column(unique = true, name = "coin_identifier")
    @JsonProperty("coin_id")
    private Integer coinId;
    @CreationTimestamp
    private Instant createdTimestamp;

    @Id
    @GeneratedValue
    private Long id;
}
