package br.com.driverapp.racingservice.query.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RacingsRepository extends JpaRepository<RacingEntity, String> {
}
