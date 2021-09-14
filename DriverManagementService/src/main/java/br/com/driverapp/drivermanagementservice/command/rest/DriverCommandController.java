package br.com.driverapp.drivermanagementservice.command.rest;

import br.com.driverapp.drivermanagementservice.command.CreateDriverCommand;
import br.com.driverapp.drivermanagementservice.command.rest.model.DriverModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class DriverCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("/drivers")
    public CompletableFuture<ResponseEntity<String>> createDriver(@RequestBody DriverModel driverModel) {

        CreateDriverCommand command = new CreateDriverCommand(
            driverModel.getId(),
            driverModel.getName(),
            driverModel.getAge(),
            driverModel.getLicenseNumber(),
            driverModel.getCar()
        );

        return commandGateway.send(command)
            .thenApply(res -> ResponseEntity.ok(String.format("Driver %s created", res)))
            .exceptionally(ex -> ResponseEntity.badRequest().body(ex.getCause().getMessage()));
    }

}
