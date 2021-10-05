package br.com.driverapp.racingservice.command.rest;

import br.com.driverapp.racingservice.command.AcceptRacingCommand;
import br.com.driverapp.racingservice.command.CancelRacingCommand;
import br.com.driverapp.racingservice.command.RequestRacingCommand;
import br.com.driverapp.racingservice.command.rest.model.AcceptRacingModel;
import br.com.driverapp.racingservice.command.rest.model.CancelRacingModel;
import br.com.driverapp.racingservice.command.rest.model.RacingModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
            racingModel.getRacingId(),
            racingModel.getPassengerId(),
            racingModel.getStartLocation(),
            racingModel.getEndLocation()
        );
        return commandGateway.send(requestRacingCommand)
            .thenApply(res -> ResponseEntity.ok().build())
            .exceptionally(ex -> ResponseEntity.badRequest().body(ex.getCause().getMessage()));
    }

    @PostMapping("/racings/driver")
    public CompletableFuture<ResponseEntity<Object>> acceptRacing(@RequestBody AcceptRacingModel acceptRacingModel) {
        AcceptRacingCommand acceptRacingCommand = new AcceptRacingCommand(
            acceptRacingModel.getRacingId(),
            acceptRacingModel.getDriverId()
        );
        return commandGateway.send(acceptRacingCommand)
                .thenApply(res -> ResponseEntity.ok().build())
                .exceptionally(ex -> ResponseEntity.badRequest().body(ex.getCause().getMessage()));
    }

    @DeleteMapping("/racings")
    public CompletableFuture<ResponseEntity<Object>> cancelRacing(@RequestBody CancelRacingModel cancelRacingModel) {
        return commandGateway.send(new CancelRacingCommand(cancelRacingModel.getRacingId()))
                .thenApply(res -> ResponseEntity.ok().build())
                .exceptionally(ex -> ResponseEntity.badRequest().body(ex.getCause().getMessage()));
    }


}
