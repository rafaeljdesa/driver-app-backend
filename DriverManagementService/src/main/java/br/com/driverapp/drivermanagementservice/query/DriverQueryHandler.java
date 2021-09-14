package br.com.driverapp.drivermanagementservice.query;

import br.com.driverapp.drivermanagementservice.query.domain.CarEntity;
import br.com.driverapp.drivermanagementservice.query.domain.DriverEntity;
import br.com.driverapp.drivermanagementservice.query.domain.DriversRepository;
import br.com.driverapp.drivermanagementservice.query.event.FindDriversQuery;
import br.com.driverapp.drivermanagementservice.query.rest.model.CarQueryModel;
import br.com.driverapp.drivermanagementservice.query.rest.model.DriverQueryModel;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DriverQueryHandler {

    @Autowired
    private DriversRepository driversRepository;

    @QueryHandler
    public List<DriverQueryModel> handle(FindDriversQuery findDriversQuery) {
        List<DriverEntity> drivers = driversRepository.findAll();
        List<DriverQueryModel> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(drivers)) {
            result = drivers.stream()
                .map(this::from)
                .collect(Collectors.toList());
        }
        return result;
    }

    private DriverQueryModel from(DriverEntity driver) {
        return new DriverQueryModel(
            driver.getName(),
            driver.getAge(),
            driver.getLicenseNumber(),
            from(driver.getCar())
        );
    }

    private CarQueryModel from(CarEntity car) {
        return new CarQueryModel(
            car.getModel(),
            car.getLicensePlate(),
            car.getColor()
        );
    }

}
