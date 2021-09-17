package br.com.driverapp.passengermanagementservice.command.domain;

import br.com.driverapp.passengermanagementservice.command.CreatePassengerCommand;
import br.com.driverapp.passengermanagementservice.command.event.PassengerCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Aggregate
public class Passenger {

    @AggregateIdentifier
    private String id;

    private String name;

    @CommandHandler
    public Passenger(CreatePassengerCommand createPassengerCommand) {
        if (createPassengerCommand.getId() != null) {
            throw new IllegalArgumentException("The passenger id must be null");
        }
        String id = UUID.randomUUID().toString();
        apply(new PassengerCreatedEvent(
            id,
            createPassengerCommand.getName()
        ));
    }

    @EventSourcingHandler
    public void on(PassengerCreatedEvent passengerCreatedEvent) {
        this.id = passengerCreatedEvent.getId();
        this.name = passengerCreatedEvent.getName();
    }
}
