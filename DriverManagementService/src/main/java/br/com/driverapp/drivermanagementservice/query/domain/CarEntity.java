package br.com.driverapp.drivermanagementservice.query.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class CarEntity {

    @Id
    @Column(unique = true)
    private String id;

    private String model;

    private String licensePlate;

    private String color;

}
