package org.RestAPI.Project3.services;

import org.RestAPI.Project3.models.Measurements;
import org.RestAPI.Project3.repositories.MeasurementRepository;
import org.RestAPI.Project3.util.MeasurementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final WeatherSensorService weatherSensorService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, WeatherSensorService weatherSensorService) {
        this.measurementRepository = measurementRepository;
        this.weatherSensorService = weatherSensorService;
    }

    public List<Measurements> findAll(){
        return measurementRepository.findAll();
    }

    public Measurements findOne(int id){
        return measurementRepository.findById(id).orElseThrow(MeasurementNotFoundException::new);
    }

    @Transactional
    public void save(Measurements measurements){
        enrichMeasurement(measurements);
        measurementRepository.save(measurements);
    }

    public void enrichMeasurement(Measurements measurements){
        measurements.setSensor(weatherSensorService.findByName(measurements.getSensor().getName()).get());
        measurements.setCreatedAt(new Date());
    }
}
