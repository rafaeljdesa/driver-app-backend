package br.com.driverapp.racingservice.command.domain;

import br.com.driverapp.racingservice.command.BillingCommand;
import br.com.driverapp.racingservice.command.CancelRacingCommand;
import br.com.driverapp.racingservice.command.RequestDriverCommand;
import br.com.driverapp.racingservice.command.UpdateDriverRacingCommand;
import br.com.driverapp.racingservice.command.events.RacingAcceptedEvent;
import br.com.driverapp.racingservice.command.events.RacingCanceledEvent;
import br.com.driverapp.racingservice.command.events.RacingRequestedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class RacingSaga {

    public RacingSaga() { }

    private static final Logger LOGGER = LoggerFactory.getLogger(RacingSaga.class);

    @Autowired
    private CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "racingId")
    public void on(RacingRequestedEvent racingRequestedEvent) {
        LOGGER.info("RacingSaga -> RacingRequestedEvent");
        RequestDriverCommand requestDriverCommand = new RequestDriverCommand(
            racingRequestedEvent.getRacingId()
        );
        BillingCommand billingCommand = new BillingCommand(
            racingRequestedEvent.getRacingId()
        );
        commandGateway.send(requestDriverCommand);
        commandGateway.send(billingCommand);
    }

    @SagaEventHandler(associationProperty = "racingId")
    public void on(RacingAcceptedEvent racingAcceptedEvent) {
        LOGGER.info("RacingSaga -> RacingAcceptedEvent");
        UpdateDriverRacingCommand updateDriverCommand = new UpdateDriverRacingCommand(
            racingAcceptedEvent.getRacingId(),
            racingAcceptedEvent.getDriverId()
        );
        commandGateway.send(updateDriverCommand);
    }

    @SagaEventHandler(associationProperty = "racingId")
    public void on(RacingCanceledEvent racingCanceledEvent) {
        LOGGER.info("RacingSaga -> RacingCanceledEvent");
        CancelRacingCommand cancelRacingCommand = new CancelRacingCommand(
            racingCanceledEvent.getRacingId()
        );
        commandGateway.send(cancelRacingCommand);
    }


}
