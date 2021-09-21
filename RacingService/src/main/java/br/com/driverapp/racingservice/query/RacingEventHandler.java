package br.com.driverapp.racingservice.query;

import br.com.driverapp.racingservice.command.domain.RacingStatus;
import br.com.driverapp.racingservice.command.events.RacingAcceptedEvent;
import br.com.driverapp.racingservice.command.events.RacingCanceledEvent;
import br.com.driverapp.racingservice.command.events.RacingRequestedEvent;
import br.com.driverapp.racingservice.query.domain.RacingEntity;
import br.com.driverapp.racingservice.query.domain.RacingsRepository;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RacingEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RacingEventHandler.class);

    @Autowired
    private RacingsRepository racingsRepository;

    @EventHandler
    public void on(RacingRequestedEvent racingRequestedEvent) {
        LOGGER.info("RacingEventHandler -> RacingRequestedEvent");
        RacingEntity racingEntity = new RacingEntity();
        BeanUtils.copyProperties(racingRequestedEvent, racingEntity);
        racingsRepository.save(racingEntity);
    }

    @EventHandler
    public void on(RacingAcceptedEvent racingAcceptedEvent) {
        LOGGER.info("RacingEventHandler -> RacingAcceptedEvent");
        Optional<RacingEntity> optionalRacing = racingsRepository.findById(racingAcceptedEvent.getRacingId());
        if (optionalRacing.isPresent()) {
            RacingEntity racingEntity = optionalRacing.get();
            racingEntity.setDriverId(racingAcceptedEvent.getDriverId());
            racingsRepository.save(racingEntity);
        }
    }

    @EventHandler
    public void on(RacingCanceledEvent racingCanceledEvent) {
        LOGGER.info("RacingEventHandler -> RacingCanceledEvent");
        Optional<RacingEntity> optionalRacing = racingsRepository.findById(racingCanceledEvent.getRacingId());
        if (optionalRacing.isPresent()) {
            RacingEntity racingEntity = optionalRacing.get();
            racingEntity.setStatus(RacingStatus.CANCELED);
            racingsRepository.save(racingEntity);
        }
    }



}
