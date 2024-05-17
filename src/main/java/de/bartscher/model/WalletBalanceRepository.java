package de.bartscher.model;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WalletBalanceRepository implements PanacheRepository<WalletBalance> {

}
