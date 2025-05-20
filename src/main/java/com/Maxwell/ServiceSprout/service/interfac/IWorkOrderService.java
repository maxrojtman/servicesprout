package com.Maxwell.ServiceSprout.service.interfac;
import java.time.LocalDateTime;


import org.springframework.web.multipart.MultipartFile;

import com.Maxwell.ServiceSprout.dto.Response;

public interface IWorkOrderService {
    Response createWorkOrder(MultipartFile photo, String title, String description, String status, String priority, LocalDateTime dueDate);
    Response getAllWorkOrders();
    Response deleteWorkOrder(Long id);
    Response updateWorkOrder(Long id, MultipartFile photo, String title, String description, String status, String priority, LocalDateTime dueDate);
    Response getWorkOrderById(String id);
    Response getOpenWorkOrdersByPriority(String status, String priority);
    Response getWorkOrdersBeforeDueDate(LocalDateTime dueDate);
}
