package br.com.driverapp.racingservice.command.domain;

import br.com.driverapp.racingservice.command.*;
import br.com.driverapp.racingservice.command.events.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

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
            requestRacingCommand.getRacingId(),
            requestRacingCommand.getPassengerId(),
            requestRacingCommand.getStartLocation(),
            requestRacingCommand.getStartLocation(),
            RacingStatus.CREATED,
            LocalDateTime.now()
        ));
    }

    @CommandHandler
    public void handle(AcceptRacingCommand acceptRacingCommand) {
        LOGGER.info("RacingAggregate -> AcceptRacingCommand");
        apply(new RacingAcceptedEvent(
            acceptRacingCommand.getRacingId(),
            acceptRacingCommand.getDriverId()
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

    @CommandHandler
    public void handle(CancelBillingCommand cancelBillingCommand) {
        LOGGER.info("RacingAggregate -> CancelBillingCommand");
    }

    @CommandHandler
    public void handle(StartRacingCommand startRacingCommand) {
        LOGGER.info("RacingAggregate -> StartRacingCommand");
        if (this.status != RacingStatus.CREATED) {
            throw new IllegalStateException("The racing must be with status CREATED");
        }
        apply(new RacingStartedEvent(
            startRacingCommand.getRacingId()
        ));
    }

    @CommandHandler
    public void handle(CompleteRacingCommand completeRacingCommand) {
        LOGGER.info("RacingAggregate -> CompleteRacingCommand");
        if (this.status != RacingStatus.STARTED) {
            throw new IllegalStateException("The racing must be with status STARTED");
        }
        apply(new RacingCompletedEvent(
            completeRacingCommand.getRacingId()
        ));
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
    public void on(RacingAcceptedEvent racingAcceptedEvent) {
        LOGGER.info("RacingAggregate -> RacingAcceptedEvent");
        this.driverId = racingAcceptedEvent.getDriverId();
    }

    @EventSourcingHandler
    public void on(RacingCanceledEvent canceledEvent) {
        LOGGER.info("RacingAggregate -> RacingCanceledEvent");
        this.status = canceledEvent.getStatus();
    }

    @EventSourcingHandler
    public void on(RacingStartedEvent racingStartedEvent) {
        LOGGER.info("RacingAggregate -> RacingStartedEvent");
        this.status = racingStartedEvent.getStatus();
    }

    @EventSourcingHandler
    public void on(RacingCompletedEvent racingCompletedEvent) {
        LOGGER.info("RacingAggregate -> RacingCompletedEvent");
        this.status = racingCompletedEvent.getStatus();
    }

}
