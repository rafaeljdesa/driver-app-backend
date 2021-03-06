package br.com.driverapp.racingservice.command.rest;

import br.com.driverapp.racingservice.command.*;
import br.com.driverapp.racingservice.command.rest.model.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
public class RacingController {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private QueryGateway queryGateway;

    @PostMapping("/racings")
    public CompletableFuture<ResponseEntity<Object>> requestRacing(@RequestBody RacingModel racingModel) {
        RequestRacingCommand requestRacingCommand = new RequestRacingCommand(
            UUID.randomUUID().toString(),
            racingModel.getPassengerId(),
            racingModel.getStartLocation(),
            racingModel.getEndLocation()
        );
        return sendCommand(requestRacingCommand);
    }

    @PostMapping("/racings/driver")
    public CompletableFuture<ResponseEntity<Object>> acceptRacing(@RequestBody AcceptRacingModel acceptRacingModel) {
        AcceptRacingCommand acceptRacingCommand = new AcceptRacingCommand(
            acceptRacingModel.getRacingId(),
            acceptRacingModel.getDriverId()
        );
        return sendCommand(acceptRacingCommand);
    }

    @PostMapping("/racings/driver/start")
    public CompletableFuture<ResponseEntity<Object>> startRacing(@RequestBody StartRacingModel startRacingModel) {
        StartRacingCommand startRacingCommand = new StartRacingCommand(
                startRacingModel.getRacingId()
        );
        return sendCommand(startRacingCommand);
    }

    @PostMapping("/racings/driver/complete")
    public CompletableFuture<ResponseEntity<Object>> completeRacing(@RequestBody CompleteRacingModel completeRacingModel) {
        CompleteRacingCommand completeRacingCommand = new CompleteRacingCommand(
                completeRacingModel.getRacingId()
        );
        return sendCommand(completeRacingCommand);
    }

    @DeleteMapping("/racings")
    public CompletableFuture<ResponseEntity<Object>> cancelRacing(@RequestBody CancelRacingModel cancelRacingModel) {
        return sendCommand(new CancelRacingCommand(cancelRacingModel.getRacingId()));
    }


    private CompletableFuture<ResponseEntity<Object>> sendCommand(Object command) {
        return commandGateway.send(command)
                .thenApply(res -> ResponseEntity.ok().build())
                .exceptionally(ex -> ResponseEntity.badRequest().body(ex.getCause().getMessage()));
    }


}
