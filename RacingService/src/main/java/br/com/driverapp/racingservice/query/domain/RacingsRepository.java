package br.com.driverapp.racingservice.query.domain;

import br.com.driverapp.racingservice.command.domain.RacingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RacingsRepository extends JpaRepository<RacingEntity, String> {
    List<RacingEntity> findByStatus(RacingStatus status);
}
