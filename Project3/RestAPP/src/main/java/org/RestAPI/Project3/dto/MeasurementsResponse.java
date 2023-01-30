package org.RestAPI.Project3.dto;

import java.util.List;

public class MeasurementsResponse {

    private List<MeasurementsDTO> measurementsDTOS;

    public MeasurementsResponse(List<MeasurementsDTO> measurementsDTOS) {
        this.measurementsDTOS = measurementsDTOS;
    }

    public List<MeasurementsDTO> getMeasurementsDTOS() {
        return measurementsDTOS;
    }

    public void setMeasurementsDTOS(List<MeasurementsDTO> measurementsDTOS) {
        this.measurementsDTOS = measurementsDTOS;
    }
}
