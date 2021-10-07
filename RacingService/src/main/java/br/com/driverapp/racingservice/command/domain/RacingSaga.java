package br.com.driverapp.racingservice.command.domain;

import br.com.driverapp.racingservice.command.BillingCommand;
import br.com.driverapp.racingservice.command.CancelBillingCommand;
import br.com.driverapp.racingservice.command.events.RacingCanceledEvent;
import br.com.driverapp.racingservice.command.events.RacingRequestedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class RacingSaga {

    private static final Logger LOGGER = LoggerFactory.getLogger(RacingSaga.class);

    @Autowired
    private CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "racingId")
    public void on(RacingRequestedEvent racingRequestedEvent) {
        LOGGER.info("RacingSaga -> RacingRequestedEvent");
        BillingCommand billingCommand = new BillingCommand(
            racingRequestedEvent.getRacingId()
        );
        commandGateway.send(billingCommand);
        SagaLifecycle.end();
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "racingId")
    public void on(RacingCanceledEvent racingCanceledEvent) {
        LOGGER.info("RacingSaga -> RacingCanceledEvent");
        CancelBillingCommand cancelBillingCommand = new CancelBillingCommand(
            racingCanceledEvent.getRacingId()
        );
        commandGateway.send(cancelBillingCommand);
        SagaLifecycle.end();
    }


}
