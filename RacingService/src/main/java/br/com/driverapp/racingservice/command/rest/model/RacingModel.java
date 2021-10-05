package br.com.driverapp.racingservice.command.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RacingModel {
    private String racingId;
    private String passengerId;
    private String startLocation;
    private String endLocation;
}
