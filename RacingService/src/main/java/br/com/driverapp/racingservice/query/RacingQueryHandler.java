package br.com.driverapp.racingservice.query;

import br.com.driverapp.racingservice.command.domain.RacingStatus;
import br.com.driverapp.racingservice.command.rest.model.RacingModel;
import br.com.driverapp.racingservice.query.domain.RacingEntity;
import br.com.driverapp.racingservice.query.domain.RacingsRepository;
import br.com.driverapp.racingservice.query.events.FindAvailableRacingQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RacingQueryHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RacingEventHandler.class);

    @Autowired
    private RacingsRepository racingsRepository;

    @QueryHandler
    public List<RacingModel> handle(FindAvailableRacingQuery findAvailableRacingQuery) {
        LOGGER.info("RacingQueryHandler -> FindAvailableRacingQuery");
        List<RacingEntity> racingEntities = racingsRepository.findByStatus(RacingStatus.CREATED);
        List<RacingModel> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(racingEntities)) {
            result = racingEntities.stream().map(entity -> new RacingModel(
                entity.getRacingId(),
                entity.getPassengerId(),
                entity.getStartLocation(),
                entity.getEndLocation()
            )).collect(Collectors.toList());
        }
        return result;
    }

}
