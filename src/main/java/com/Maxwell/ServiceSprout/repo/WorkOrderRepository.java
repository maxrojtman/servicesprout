package com.Maxwell.ServiceSprout.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Maxwell.ServiceSprout.entity.WorkOrder;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {

    // Custom query methods based on naming convention

    List<WorkOrder> findByStatus(String status);

    List<WorkOrder> findByPriority(String priority);

    List<WorkOrder> findByAssignedUsers_Id(Long userId);

    List<WorkOrder> findByDueDateBefore(java.time.LocalDateTime date);

    List<WorkOrder> findByTitleContainingIgnoreCase(String keyword);
}