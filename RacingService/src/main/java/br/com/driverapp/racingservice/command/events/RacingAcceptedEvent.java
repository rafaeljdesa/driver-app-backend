package br.com.driverapp.racingservice.command.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RacingAcceptedEvent {

    private String racingId;
    private String driverId;

}
