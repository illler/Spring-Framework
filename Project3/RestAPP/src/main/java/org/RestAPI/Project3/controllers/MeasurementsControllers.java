package org.RestAPI.Project3.controllers;


import jakarta.validation.Valid;
import org.RestAPI.Project3.dto.MeasurementsDTO;
import org.RestAPI.Project3.dto.MeasurementsResponse;
import org.RestAPI.Project3.models.Measurements;
import org.RestAPI.Project3.services.MeasurementService;
import org.RestAPI.Project3.util.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementsControllers {

    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;

    public MeasurementsControllers(MeasurementService measurementService, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public MeasurementsDTO showOne(@PathVariable int id){
        return convertToDTO(measurementService.findOne(id));
    }


    @GetMapping()
    public MeasurementsResponse show(){
        return new MeasurementsResponse(measurementService.findAll().stream().map(this::convertToDTO).toList());
    }


    @GetMapping("/rainyDaysCount")
    public long rainyDays(){
        return measurementService.findAll().stream().filter(Measurements::isRaining).count();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementsDTO measurementsDTO,
                                                   BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();

            List<FieldError> list = bindingResult.getFieldErrors();
            for (FieldError fieldError : list) {
                errorMessage.append(fieldError.getField())
                        .append(" - ").append(fieldError.getDefaultMessage())
                        .append(";");
            }
            throw new SensorNotCreatedException(errorMessage.toString());
        }
        measurementService.save(convertToModel(measurementsDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(MeasurementNotFoundException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                "Measurement with this id was`t found",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(MeasurementNotCreatedException e) {
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurements convertToModel(MeasurementsDTO measurementsDTO){
        return modelMapper.map(measurementsDTO, Measurements.class);
    }

    private MeasurementsDTO convertToDTO(Measurements measurements){
        return modelMapper.map(measurements, MeasurementsDTO.class);
    }

}
