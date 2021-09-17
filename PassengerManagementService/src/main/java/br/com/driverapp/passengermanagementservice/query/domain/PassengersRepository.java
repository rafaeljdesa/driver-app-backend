package br.com.driverapp.passengermanagementservice.query.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengersRepository extends JpaRepository<PassengerEntity, String> {

    PassengerEntity findByName(String name);

}
