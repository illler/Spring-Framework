package org.RestAPI.Project3.controllers;

import jakarta.validation.Valid;
import org.RestAPI.Project3.dto.WeatherSensorDTO;
import org.RestAPI.Project3.models.WeatherSensor;
import org.RestAPI.Project3.services.WeatherSensorService;
import org.RestAPI.Project3.util.SensorErrorResponse;
import org.RestAPI.Project3.util.SensorNotCreatedException;
import org.RestAPI.Project3.util.SensorNotFoundException;
import org.RestAPI.Project3.util.SensorValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensor")
public class WeatherSensorController {

    private final WeatherSensorService weatherSensorService;
    private final ModelMapper modelMapper;

    private final SensorValidation sensorValidation;

    @Autowired
    public WeatherSensorController(WeatherSensorService weatherSensorService, ModelMapper modelMapper,
                                   SensorValidation sensorValidation) {
        this.weatherSensorService = weatherSensorService;
        this.modelMapper = modelMapper;
        this.sensorValidation = sensorValidation;
    }

    @GetMapping("/{id}")
    public WeatherSensorDTO showOne(@PathVariable int id){
        return convertToWeatherSensorDTO(weatherSensorService.findOne(id));
    }

    @GetMapping()
    public List<WeatherSensorDTO> showAll(){
        return weatherSensorService.findAll().stream().map(this::convertToWeatherSensorDTO).collect(Collectors.toList());
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid WeatherSensorDTO weatherSensorDTO,
                                                   BindingResult bindingResult){

        sensorValidation.validate(convertToWeatherSensor(weatherSensorDTO), bindingResult);

        if (bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> list = bindingResult.getFieldErrors();
            for (FieldError fieldError : list) {
                errorMessage.append(fieldError.getField())
                        .append(" - ").append(fieldError.getDefaultMessage()==null?fieldError.getCode():fieldError.getDefaultMessage())
                        .append(";");
            }
            throw new SensorNotCreatedException(errorMessage.toString());
        }
        weatherSensorService.save(convertToWeatherSensor(weatherSensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                "Sensor with this id was`t found",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    private WeatherSensor convertToWeatherSensor(WeatherSensorDTO weatherSensorDTO){
        return modelMapper.map(weatherSensorDTO, WeatherSensor.class);
    }

    private WeatherSensorDTO convertToWeatherSensorDTO(WeatherSensor weatherSensor){
        return modelMapper.map(weatherSensor, WeatherSensorDTO.class);
    }

}
