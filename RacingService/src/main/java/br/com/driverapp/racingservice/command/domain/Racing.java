package br.com.driverapp.racingservice.command.domain;

import br.com.driverapp.racingservice.command.BillingCommand;
import br.com.driverapp.racingservice.command.CancelRacingCommand;
import br.com.driverapp.racingservice.command.RequestRacingCommand;
import br.com.driverapp.racingservice.command.UpdateDriverRacingCommand;
import br.com.driverapp.racingservice.command.events.RacingDriverUpdatedEvent;
import br.com.driverapp.racingservice.command.events.RacingCanceledEvent;
import br.com.driverapp.racingservice.command.events.RacingRequestedEvent;
import br.com.driverapp.racingservice.query.RacingEventHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Aggregate
public class Racing {

    private static final Logger LOGGER = LoggerFactory.getLogger(Racing.class);

    @AggregateIdentifier
    private String racingId;
    private String passengerId;
    private String driverId;
    private String startLocation;
    private String endLocation;
    private RacingStatus status;
    private LocalDateTime timestamp;

    @CommandHandler
    public Racing(RequestRacingCommand requestRacingCommand) {
        LOGGER.info("RacingAggregate -> RequestRacingCommand");
        apply(new RacingRequestedEvent(
            UUID.randomUUID().toString(),
            requestRacingCommand.getPassengerId(),
            requestRacingCommand.getStartLocation(),
            requestRacingCommand.getStartLocation(),
            RacingStatus.CREATED,
            LocalDateTime.now()
        ));
    }

    @CommandHandler
    public void handle(UpdateDriverRacingCommand updateDriverCommand) {
        LOGGER.info("RacingAggregate -> UpdateDriverRacingCommand");
        apply(new RacingDriverUpdatedEvent(
            updateDriverCommand.getDriverId()
        ));
    }

    @CommandHandler
    public void handle(CancelRacingCommand cancelRacingCommand) {
        LOGGER.info("RacingAggregate -> CancelRacingCommand");
        apply(new RacingCanceledEvent(
            cancelRacingCommand.getRacingId()
        ));
    }

    @CommandHandler
    public void handle(BillingCommand billingCommand) {
        LOGGER.info("RacingAggregate -> BillingCommand");
    }

    @EventSourcingHandler
    public void on(RacingRequestedEvent racingRequestedEvent) {
        LOGGER.info("RacingAggregate -> RacingRequestedEvent");
        this.racingId = racingRequestedEvent.getRacingId();
        this.passengerId = racingRequestedEvent.getPassengerId();
        this.startLocation = racingRequestedEvent.getStartLocation();
        this.endLocation = racingRequestedEvent.getEndLocation();
        this.status = racingRequestedEvent.getStatus();
    }

    @EventSourcingHandler
    public void on(RacingDriverUpdatedEvent driverUpdatedEvent) {
        LOGGER.info("RacingAggregate -> RacingDriverUpdatedEvent");
        this.driverId = driverUpdatedEvent.getDriverId();
    }

    @EventSourcingHandler
    public void on(RacingCanceledEvent canceledEvent) {
        LOGGER.info("RacingAggregate -> RacingCanceledEvent");
        this.status = canceledEvent.getStatus();
    }

}
