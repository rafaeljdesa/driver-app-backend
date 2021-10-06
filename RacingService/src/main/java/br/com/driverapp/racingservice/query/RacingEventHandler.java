package br.com.driverapp.racingservice.query;

import br.com.driverapp.racingservice.command.domain.RacingStatus;
import br.com.driverapp.racingservice.command.events.RacingAcceptedEvent;
import br.com.driverapp.racingservice.command.events.RacingCanceledEvent;
import br.com.driverapp.racingservice.command.events.RacingRequestedEvent;
import br.com.driverapp.racingservice.command.events.RacingStartedEvent;
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

    @EventHandler
    public void on(RacingStartedEvent racingStartedEvent) {
        LOGGER.info("RacingEventHandler -> RacingStartedEvent");
        Optional<RacingEntity> optionalRacing = racingsRepository.findById(racingStartedEvent.getRacingId());
        if (optionalRacing.isPresent()) {
            RacingEntity racingEntity = optionalRacing.get();
            racingEntity.setStatus(RacingStatus.STARTED);
            racingsRepository.save(racingEntity);
        }
    }



}
