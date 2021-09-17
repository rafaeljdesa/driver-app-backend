package br.com.driverapp.passengermanagementservice.query;

import br.com.driverapp.passengermanagementservice.command.event.PassengerCreatedEvent;
import br.com.driverapp.passengermanagementservice.query.domain.PassengerEntity;
import br.com.driverapp.passengermanagementservice.query.domain.PassengersRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class PassengerEventHandler {

    private final PassengersRepository passengerRepository;

    public PassengerEventHandler(PassengersRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @EventHandler
    public void on(PassengerCreatedEvent passengerCreatedEvent) {
        PassengerEntity passengerEntity = new PassengerEntity(
            passengerCreatedEvent.getId(),
            passengerCreatedEvent.getName()
        );
        passengerRepository.save(passengerEntity);
    }

}
