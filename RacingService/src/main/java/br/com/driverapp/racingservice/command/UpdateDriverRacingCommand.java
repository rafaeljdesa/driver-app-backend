package br.com.driverapp.racingservice.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDriverRacingCommand {

    @TargetAggregateIdentifier
    private String racingId;
    private String driverId;

}
