package br.com.driverapp.passengermanagementservice.query;

import br.com.driverapp.passengermanagementservice.query.domain.PassengerEntity;
import br.com.driverapp.passengermanagementservice.query.domain.PassengersRepository;
import br.com.driverapp.passengermanagementservice.query.event.FindPassengersQuery;
import br.com.driverapp.passengermanagementservice.query.rest.model.PassengerQueryModel;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PassengerQueryHandler {

    public PassengerQueryHandler() {
    }

    @Autowired
    private PassengersRepository passengerRepository;

    @QueryHandler
    public List<PassengerQueryModel> handle(FindPassengersQuery findPassengersQuery) {
        List<PassengerEntity> passengers = passengerRepository.findAll();
        List<PassengerQueryModel> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(passengers)) {
            result = passengers.stream().map(p -> new PassengerQueryModel(p.getName())).collect(Collectors.toList());
        }
        return result;
    }
}
