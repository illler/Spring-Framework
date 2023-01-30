package org.RestAPI.Project3.repositories;

import org.RestAPI.Project3.models.WeatherSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherSensorRepository extends JpaRepository<WeatherSensor, Integer> {

    Optional<WeatherSensor> findByName(String name);
}
