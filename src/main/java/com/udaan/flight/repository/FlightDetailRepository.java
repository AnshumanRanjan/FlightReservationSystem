package com.udaan.flight.repository;

import com.udaan.flight.model.FlightDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface FlightDetailRepository extends JpaRepository<FlightDetail,Long> {
    FlightDetail findByFlightno(long flightno);

}