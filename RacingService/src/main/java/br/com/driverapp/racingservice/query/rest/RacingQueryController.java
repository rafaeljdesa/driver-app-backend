package br.com.driverapp.racingservice.query.rest;

import br.com.driverapp.racingservice.command.rest.model.RacingModel;
import br.com.driverapp.racingservice.query.RacingEventHandler;
import br.com.driverapp.racingservice.query.events.FindAvailableRacingQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Stream;

@RestController
public class RacingQueryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RacingQueryController.class);

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping(value = "/driver-online", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<RacingModel> online() {
        LOGGER.info("RacingQueryController - online");
        SubscriptionQueryResult<List<RacingModel>, RacingModel> availableRacings = queryGateway.subscriptionQuery(
                new FindAvailableRacingQuery(),
                ResponseTypes.multipleInstancesOf(RacingModel.class),
                ResponseTypes.instanceOf(RacingModel.class)
        );
        return Flux.create(emitter -> {
            availableRacings.initialResult()
                    .flatMapMany(Flux::fromIterable)
                    .subscribe(initial -> {
                        LOGGER.info("RacingQueryController - Flux - Initial");
                        if (initial != null) {
                            emitter.next(initial);
                        }
                    });
            availableRacings.updates()
                    .doOnComplete(emitter::complete)
                    .subscribe(update -> {
                        LOGGER.info("RacingQueryController - Flux - Update");
                        emitter.next(update);
                    });
        });
    }

    @GetMapping("/racings")
    public CompletableFuture<ResponseEntity<List<RacingModel>>> getRacings() {
        LOGGER.info("RacingQueryController - getRacings");
        CompletableFuture<List<RacingModel>> racings = queryGateway.query(
                new FindAvailableRacingQuery(),
                ResponseTypes.multipleInstancesOf(RacingModel.class)
        );
        return racings.thenApply(ResponseEntity::ok);
    }

}
