package br.com.driverapp.passengermanagementservice.command.rest;

import br.com.driverapp.passengermanagementservice.command.CreatePassengerCommand;
import br.com.driverapp.passengermanagementservice.command.rest.model.PassengerModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class PassengerCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("/passengers")
    public CompletableFuture<ResponseEntity<String>> createPassenger(@RequestBody PassengerModel passengerModel) {
        CreatePassengerCommand createPassengerCommand = new CreatePassengerCommand(
            passengerModel.getId(),
            passengerModel.getName()
        );
        return commandGateway.send(createPassengerCommand)
            .thenApply(res -> ResponseEntity.ok(String.format("Passenger with id %s created", res)))
            .exceptionally(ex -> {
                if (ex.getCause() instanceof IllegalArgumentException) {
                    return ResponseEntity.badRequest().body(ex.getCause().getMessage());
                }
                return ResponseEntity.internalServerError().build();
            });
    }

}
