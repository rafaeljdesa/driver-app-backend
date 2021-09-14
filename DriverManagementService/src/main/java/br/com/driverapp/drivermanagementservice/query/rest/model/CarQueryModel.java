package br.com.driverapp.drivermanagementservice.query.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarQueryModel {

    private String model;

    private String licensePlate;

    private String color;

}
