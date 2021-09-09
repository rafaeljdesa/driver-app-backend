package br.com.driverapp.drivermanagementservice.command.event;

import br.com.driverapp.drivermanagementservice.command.domain.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverCreatedEvent {

    private String id;
    private String name;
    private int age;
    private String licenseNumber;
    private Car car;

}
