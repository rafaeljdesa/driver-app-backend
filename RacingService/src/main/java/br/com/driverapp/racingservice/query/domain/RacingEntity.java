package br.com.driverapp.racingservice.query.domain;

import br.com.driverapp.racingservice.command.domain.RacingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "racings")
public class RacingEntity implements Serializable {

    private static final long serialVersionUID = -3430434527413784928L;

    @Id
    private String racingId;
    private String passengerId;
    private String driverId;
    private String startLocation;
    private String endLocation;
    @Enumerated(EnumType.STRING)
    private RacingStatus status;
    private LocalDateTime timestamp;

}
