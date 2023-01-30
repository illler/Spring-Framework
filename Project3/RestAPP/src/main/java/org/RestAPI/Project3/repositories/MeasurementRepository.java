package org.RestAPI.Project3.repositories;

import org.RestAPI.Project3.models.Measurements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurements, Integer> {
}
