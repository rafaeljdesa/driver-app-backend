package br.com.driverapp.passengermanagementservice.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePassengerCommand {

    @TargetAggregateIdentifier
    private String id;
    private String name;

}
