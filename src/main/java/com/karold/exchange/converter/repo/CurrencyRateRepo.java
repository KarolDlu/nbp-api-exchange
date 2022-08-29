package com.karold.exchange.converter.repo;

import com.karold.exchange.model.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CurrencyRateRepo extends JpaRepository<CurrencyRate, Long> {

    Optional<CurrencyRate> findByCodeAndRateEffectiveDate(String code, LocalDate effectiveDate);
}
