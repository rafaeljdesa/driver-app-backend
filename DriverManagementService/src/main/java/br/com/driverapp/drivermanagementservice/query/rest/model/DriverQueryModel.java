package br.com.driverapp.drivermanagementservice.query.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverQueryModel {

    private String name;

    private int age;

    private String licenseNumber;

    private CarQueryModel car;

}
