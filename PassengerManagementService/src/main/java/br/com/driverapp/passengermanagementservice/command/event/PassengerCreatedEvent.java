package br.com.driverapp.passengermanagementservice.command.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerCreatedEvent {

    private String id;
    private String name;

}
