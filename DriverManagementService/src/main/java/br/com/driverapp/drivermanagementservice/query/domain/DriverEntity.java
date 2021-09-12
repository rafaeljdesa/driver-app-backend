package br.com.driverapp.drivermanagementservice.query.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "drivers")
public class DriverEntity {

    @Id
    @Column(unique = true)
    private String id;

    private String name;

    private int age;

    private String licenseNumber;

    @OneToOne
    private CarEntity car;

}


