package org.RestAPI.Project3.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class WeatherSensorDTO{

    @NotNull(message = "Name should be not empty")
    @Size(min = 3, max = 30, message = "Name should be less than 30 and bigger than 3 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
