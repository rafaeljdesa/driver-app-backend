package br.com.driverapp.drivermanagementservice.command.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarModel {

    private String id;

    private String model;

    private String licensePlate;

    private String color;

}
