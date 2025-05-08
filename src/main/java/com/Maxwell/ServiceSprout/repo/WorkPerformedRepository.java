package com.Maxwell.ServiceSprout.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Maxwell.ServiceSprout.entity.WorkPerformed;

@Repository
public interface WorkPerformedRepository extends JpaRepository<WorkPerformed, Long> {

    // Find all work performed logs for a specific work order
    List<WorkPerformed> findByWorkOrder_Id(Long workOrderId);

    // Find all logs created by a specific technician
    List<WorkPerformed> findByTechnician_Id(Long technicianId);

    // Find all logs within a time range
    List<WorkPerformed> findByTimestampBetween(java.time.LocalDateTime start, java.time.LocalDateTime end);

    // Optional: Find logs by technician and work order together
    List<WorkPerformed> findByTechnician_IdAndWorkOrder_Id(Long technicianId, Long workOrderId);
}
