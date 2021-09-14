package br.com.driverapp.drivermanagementservice.query.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindDriversQuery {

    private String criteria = "findAll";

}
