package br.com.driverapp.drivermanagementservice.command.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.EntityId;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @EntityId
    private String id;

    private String model;

    private String licensePlate;

    private String color;

}
