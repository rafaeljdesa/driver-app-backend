package br.com.driverapp.racingservice.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelRacingCommand {

    @TargetAggregateIdentifier
    private String racingId;

}
