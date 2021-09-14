package br.com.driverapp.drivermanagementservice.query.rest;

import br.com.driverapp.drivermanagementservice.query.event.FindDriversQuery;
import br.com.driverapp.drivermanagementservice.query.rest.model.DriverQueryModel;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class DriverQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/drivers")
    public CompletableFuture<ResponseEntity<List<DriverQueryModel>>> getDrivers() {

        FindDriversQuery findDriversQuery = new FindDriversQuery();

        return queryGateway.query(findDriversQuery, ResponseTypes.multipleInstancesOf(DriverQueryModel.class))
                .thenApply(ResponseEntity::ok);
    }

}
