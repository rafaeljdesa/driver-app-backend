package br.com.driverapp.drivermanagementservice.query.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "drivers")
public class DriverEntity implements Serializable {

    private static final long serialVersionUID = -7345126207024652398L;

    @Id
    private String id;

    private String name;

    private int age;

    private String licenseNumber;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private CarEntity car;

}


