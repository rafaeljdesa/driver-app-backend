package br.com.driverapp.drivermanagementservice.command.rest;

import br.com.driverapp.drivermanagementservice.command.CreateDriverCommand;
import br.com.driverapp.drivermanagementservice.command.rest.model.DriverModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class DriverController {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private QueryGateway queryGateway;

    @PostMapping("/drivers")
    public ResponseEntity<String> createDriver(@RequestBody DriverModel driverModel) {

        CompletableFuture<String> result = commandGateway.send(new CreateDriverCommand(
                driverModel.getId(),
                driverModel.getName(),
                driverModel.getAge(),
                driverModel.getLicenseNumber(),
                driverModel.getCar()
        ));

        return ResponseEntity.ok("The driver will be created");
    }

    @GetMapping("/drivers")
    public ResponseEntity<?> getDrivers(@RequestBody DriverModel driver) {

        return ResponseEntity.ok().build();
    }
}
