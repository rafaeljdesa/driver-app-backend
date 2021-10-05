package br.com.driverapp.racingservice.query.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindAvailableRacingQuery {
    private String criteria = "findByStatus";
}
