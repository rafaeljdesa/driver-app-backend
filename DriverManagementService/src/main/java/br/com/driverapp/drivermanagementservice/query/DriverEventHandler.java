package br.com.driverapp.drivermanagementservice.query;

import br.com.driverapp.drivermanagementservice.command.domain.Car;
import br.com.driverapp.drivermanagementservice.command.event.DriverCreatedEvent;
import br.com.driverapp.drivermanagementservice.query.domain.CarEntity;
import br.com.driverapp.drivermanagementservice.query.domain.DriverEntity;
import br.com.driverapp.drivermanagementservice.query.domain.DriversRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.stereotype.Component;

@Component
public class DriverEventHandler {

    private final DriversRepository driversRepository;

    public DriverEventHandler(DriversRepository driversRepository) {
        this.driversRepository = driversRepository;
    }

    @ExceptionHandler(resultType = IllegalArgumentException.class)
    public void handle(IllegalArgumentException ex) {
        throw ex;
    }

    @EventHandler
    public void on(DriverCreatedEvent driverCreatedEvent) {
        DriverEntity driver = new DriverEntity(
            driverCreatedEvent.getId(),
            driverCreatedEvent.getName(),
            driverCreatedEvent.getAge(),
            driverCreatedEvent.getLicenseNumber(),
            from(driverCreatedEvent.getCar())
        );
        driversRepository.save(driver);
    }

    private CarEntity from(Car car) {
        return new CarEntity(
            car.getId(),
            car.getModel(),
            car.getLicensePlate(),
            car.getColor()
        );
    }

}
