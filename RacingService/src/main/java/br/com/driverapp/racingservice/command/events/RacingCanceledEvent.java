package br.com.driverapp.racingservice.command.events;

import br.com.driverapp.racingservice.command.domain.RacingStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RacingCanceledEvent {

    public RacingCanceledEvent(String racingId) {
        this.racingId = racingId;
    }

    private String racingId;
    private RacingStatus status = RacingStatus.CANCELED;

}
