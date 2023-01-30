package org.RestAPI.Project3.dto;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.RestAPI.Project3.models.WeatherSensor;

public class MeasurementsDTO {
    @NotNull(message = "Value of temperature should not be empty")
    @Min(-100)
    @Max(100)
    private Double value;

    @NotNull(message = "Raining value should be True or False, not empty")
    private Boolean raining;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "sensor", referencedColumnName = "name")
    private WeatherSensor sensor;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean isRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public WeatherSensor getSensor() {
        return sensor;
    }

    public void setSensor(WeatherSensor sensor) {
        this.sensor = sensor;
    }
}
