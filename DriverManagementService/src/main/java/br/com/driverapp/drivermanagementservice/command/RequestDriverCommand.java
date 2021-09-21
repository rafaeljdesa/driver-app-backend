package br.com.driverapp.drivermanagementservice.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDriverCommand {

    @TargetAggregateIdentifier
    private String racingId;

}
