package br.com.driverapp.drivermanagementservice.command;

import br.com.driverapp.drivermanagementservice.command.rest.model.CarModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDriverCommand {

    @TargetAggregateIdentifier
    private String id;
    private String name;
    private int age;
    private String licenseNumber;
    private CarModel car;

}
