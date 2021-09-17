package br.com.driverapp.passengermanagementservice.query.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "passengers")
public class PassengerEntity {

    @Id
    private String id;

    private String name;

}
