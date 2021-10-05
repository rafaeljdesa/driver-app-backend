package br.com.driverapp.drivermanagementservice.command.domain;

import br.com.driverapp.drivermanagementservice.command.CreateDriverCommand;
import br.com.driverapp.drivermanagementservice.command.event.DriverCreatedEvent;
import br.com.driverapp.drivermanagementservice.command.rest.model.CarModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Aggregate
public class Driver {

    @AggregateIdentifier
    private String id;

    private String name;

    private int age;

    private String licenseNumber;

    @AggregateMember
    private Car car;

    @CommandHandler
    public Driver(CreateDriverCommand createDriverCommand) {
        if (createDriverCommand.getId() != null) {
            throw new IllegalArgumentException("The driver id must be null");
        }
        if (createDriverCommand.getAge() < 21) {
            throw new IllegalArgumentException("The minimum age is 21");
        }
        apply(new DriverCreatedEvent(
            UUID.randomUUID().toString(),
            createDriverCommand.getName(),
            createDriverCommand.getAge(),
            createDriverCommand.getLicenseNumber(),
            from(createDriverCommand.getCar())
        ));
    }

    @EventSourcingHandler
    public void on(DriverCreatedEvent driverCreatedEvent) {
        this.id = driverCreatedEvent.getId();
        this.name = driverCreatedEvent.getName();
        this.age = driverCreatedEvent.getAge();
        this.licenseNumber = driverCreatedEvent.getLicenseNumber();
        this.car = driverCreatedEvent.getCar();
    }

    private Car from(CarModel carModel) {
        if (carModel.getId() != null) {
            throw new IllegalArgumentException("The car id must be null");
        }
        return new Car(
            UUID.randomUUID().toString(),
            carModel.getModel(),
            carModel.getLicensePlate(),
            carModel.getColor()
        );
    }
}
