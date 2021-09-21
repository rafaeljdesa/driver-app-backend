package br.com.driverapp.racingservice.command.events;

import br.com.driverapp.racingservice.command.domain.RacingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RacingRequestedEvent {

    private String racingId;
    private String passengerId;
    private String startLocation;
    private String endLocation;
    private RacingStatus status;
    private LocalDateTime timestamp;

}
