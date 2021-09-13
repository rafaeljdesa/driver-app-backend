package br.com.driverapp.drivermanagementservice.query.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class CarEntity implements Serializable {

    private static final long serialVersionUID = -600833625530308226L;

    @Id
    private String id;

    private String model;

    private String licensePlate;

    private String color;

}
