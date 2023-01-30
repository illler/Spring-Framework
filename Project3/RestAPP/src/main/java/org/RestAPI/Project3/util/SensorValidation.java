package org.RestAPI.Project3.util;

import org.RestAPI.Project3.models.WeatherSensor;
import org.RestAPI.Project3.services.WeatherSensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidation implements Validator {

    private final WeatherSensorService weatherSensorService;

    @Autowired
    public SensorValidation(WeatherSensorService weatherSensorService) {
        this.weatherSensorService = weatherSensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return WeatherSensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        WeatherSensor weatherSensor = (WeatherSensor) target;
        if (weatherSensorService.findByName(weatherSensor.getName()).isPresent()){
            errors.rejectValue("name", "Sensor with this name is already exist!");
        }
    }
}
