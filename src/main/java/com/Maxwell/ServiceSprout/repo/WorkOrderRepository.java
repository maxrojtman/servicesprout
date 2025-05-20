package com.Maxwell.ServiceSprout.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Maxwell.ServiceSprout.entity.WorkOrder;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {

    List<WorkOrder> findByStatus(String status);

    List<WorkOrder> findByPriority(String priority);

    List<WorkOrder> findByAssignedUsersId(Long userId);

    List<WorkOrder> findByTitleContainingIgnoreCase(String keyword);
    
    List<WorkOrder> findByStatusAndPriority(String status, String priority);

    List<WorkOrder> findByDueDateBefore(LocalDateTime dueDate);
}