package org.RestAPI.Project3.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "measurements")
public class Measurements{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Measurements() {
    }

    public Measurements(Double value, boolean raining, WeatherSensor sensor) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
