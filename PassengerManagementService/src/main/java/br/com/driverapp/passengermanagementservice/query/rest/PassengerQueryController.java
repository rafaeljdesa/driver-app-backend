package br.com.driverapp.passengermanagementservice.query.rest;

import br.com.driverapp.passengermanagementservice.query.event.FindPassengersQuery;
import br.com.driverapp.passengermanagementservice.query.rest.model.PassengerQueryModel;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class PassengerQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/passengers")
    public CompletableFuture<ResponseEntity<List<PassengerQueryModel>>> findAllPassengers() {
        return queryGateway.query(new FindPassengersQuery(), ResponseTypes.multipleInstancesOf(PassengerQueryModel.class))
            .thenApply(ResponseEntity::ok);
    }
}
