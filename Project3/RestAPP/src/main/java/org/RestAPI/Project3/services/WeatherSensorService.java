package org.RestAPI.Project3.services;

import org.RestAPI.Project3.models.WeatherSensor;
import org.RestAPI.Project3.repositories.WeatherSensorRepository;
import org.RestAPI.Project3.util.SensorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class WeatherSensorService {

    private final WeatherSensorRepository weatherSensorRepository;

    @Autowired
    public WeatherSensorService(WeatherSensorRepository weatherSensorRepository) {
        this.weatherSensorRepository = weatherSensorRepository;
    }

    public List<WeatherSensor> findAll(){
        return weatherSensorRepository.findAll();
    }

    public WeatherSensor findOne(int id){
        return weatherSensorRepository.findById(id).orElseThrow(SensorNotFoundException::new);
    }

    @Transactional
    public void save(WeatherSensor weatherSensor){
        weatherSensorRepository.save(weatherSensor);
    }

    public Optional<WeatherSensor> findByName(String name) {
        return weatherSensorRepository.findByName(name);
    }


}
