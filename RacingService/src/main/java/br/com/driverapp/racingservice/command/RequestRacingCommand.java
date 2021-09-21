package br.com.driverapp.racingservice.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestRacingCommand {

    @TargetAggregateIdentifier
    private String racingId;
    private String passengerId;
    private String startLocation;
    private String endLocation;

}
