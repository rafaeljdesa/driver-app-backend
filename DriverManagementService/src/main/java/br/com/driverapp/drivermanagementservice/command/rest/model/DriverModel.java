package br.com.driverapp.drivermanagementservice.command.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverModel {

    private String id;

    private String name;

    private int age;

    private String licenseNumber;

    private CarModel car;

}
