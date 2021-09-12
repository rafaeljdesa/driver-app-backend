package br.com.driverapp.drivermanagementservice.query.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DriversRepository extends JpaRepository<DriverEntity, String> {
}
