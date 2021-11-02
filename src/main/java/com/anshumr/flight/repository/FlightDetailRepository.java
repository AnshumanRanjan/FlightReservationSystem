package com.anshumr.flight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.anshumr.flight.model.FlightDetail;

@Service
public interface FlightDetailRepository extends JpaRepository<FlightDetail,Long> {
    FlightDetail findByFlightno(long flightno);

}