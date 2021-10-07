package br.com.driverapp.racingservice.query;

import br.com.driverapp.racingservice.command.domain.RacingStatus;
import br.com.driverapp.racingservice.command.events.*;
import br.com.driverapp.racingservice.command.rest.model.RacingModel;
import br.com.driverapp.racingservice.query.domain.RacingEntity;
import br.com.driverapp.racingservice.query.domain.RacingsRepository;
import br.com.driverapp.racingservice.query.events.FindAvailableRacingQuery;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
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

    @Autowired
    private QueryUpdateEmitter queryUpdateEmitter;

    @EventHandler
    public void on(RacingRequestedEvent racingRequestedEvent) {
        LOGGER.info("RacingEventHandler -> RacingRequestedEvent");
        RacingEntity racingEntity = new RacingEntity();
        BeanUtils.copyProperties(racingRequestedEvent, racingEntity);
        racingsRepository.save(racingEntity);

        // Updating driver's subscription query
        LOGGER.info("RacingEventHandler -> Updating subscription query");
        RacingModel racingModel = new RacingModel(
            racingEntity.getRacingId(),
            racingEntity.getPassengerId(),
            racingEntity.getStartLocation(),
            racingEntity.getEndLocation()
        );
        queryUpdateEmitter.emit(FindAvailableRacingQuery.class, query -> true, racingModel);
    }

    @EventHandler
    public void on(RacingAcceptedEvent racingAcceptedEvent) {
        LOGGER.info("RacingEventHandler -> RacingAcceptedEvent");
        updateRacingDriver(racingAcceptedEvent.getRacingId(), racingAcceptedEvent.getDriverId());
    }

    @EventHandler
    public void on(RacingCanceledEvent racingCanceledEvent) {
        LOGGER.info("RacingEventHandler -> RacingCanceledEvent");
        updateRacingStatus(racingCanceledEvent.getRacingId(), RacingStatus.CANCELED);
    }

    @EventHandler
    public void on(RacingStartedEvent racingStartedEvent) {
        LOGGER.info("RacingEventHandler -> RacingStartedEvent");
        updateRacingStatus(racingStartedEvent.getRacingId(), RacingStatus.STARTED);
    }

    @EventHandler
    public void on(RacingCompletedEvent racingCompletedEvent) {
        LOGGER.info("RacingEventHandler -> RacingCompletedEvent");
        updateRacingStatus(racingCompletedEvent.getRacingId(), RacingStatus.COMPLETED);
    }

    private void updateRacingStatus(String racingId, RacingStatus racingStatus) {
        Optional<RacingEntity> optionalRacing = racingsRepository.findById(racingId);
        if (optionalRacing.isPresent()) {
            RacingEntity racingEntity = optionalRacing.get();
            racingEntity.setStatus(racingStatus);
            racingsRepository.save(racingEntity);
        }
    }

    private void updateRacingDriver(String racingId, String driverId) {
        Optional<RacingEntity> optionalRacing = racingsRepository.findById(racingId);
        if (optionalRacing.isPresent()) {
            RacingEntity racingEntity = optionalRacing.get();
            racingEntity.setDriverId(driverId);
            racingsRepository.save(racingEntity);
        }
    }

}
