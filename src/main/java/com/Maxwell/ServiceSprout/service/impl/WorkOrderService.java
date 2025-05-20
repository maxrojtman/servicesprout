package com.Maxwell.ServiceSprout.service.impl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Maxwell.ServiceSprout.dto.Response;
import com.Maxwell.ServiceSprout.dto.WorkOrderDTO;
import com.Maxwell.ServiceSprout.entity.WorkOrder;
import com.Maxwell.ServiceSprout.exception.OurException;
import com.Maxwell.ServiceSprout.repo.WorkOrderRepository;
import com.Maxwell.ServiceSprout.service.Aws3Service;
import com.Maxwell.ServiceSprout.service.interfac.IWorkOrderService;
import com.Maxwell.ServiceSprout.utils.Utils;

@Service
public class WorkOrderService implements IWorkOrderService {
    @Autowired
    private WorkOrderRepository woRepository;
    @Autowired
    private Aws3Service awsS3Service;

    // Implement the methods from IWorkOrderService interface here
    // For example:
    @Override
    public Response createWorkOrder(MultipartFile photo, String title, String description, String status,
            String priority, LocalDateTime dueDate) {
        Response response = new Response();

        try {
            String imageUrl = (photo == null || photo.isEmpty()) ? "https://developers.elementor.com/docs/assets/img/elementor-placeholder-image.png" :  awsS3Service.saveImageToS3(photo);
            WorkOrder wo = new WorkOrder();
            wo.setPhotoUrl(imageUrl);
            wo.setTitle(title);
            wo.setDescription(description);
            wo.setStatus(status);
            wo.setPriority(priority);
            wo.setCreatedAt(LocalDateTime.now());
            wo.setDueDate(dueDate);
            WorkOrder savedWO = woRepository.save(wo);
            WorkOrderDTO woDTO = Utils.mapWorkOrderEntityToDTO(savedWO);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setWorkOrder(woDTO);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error creating work order " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllWorkOrders() {
        Response response = new Response();

        try {
            List<WorkOrder> workOrderList = woRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<WorkOrderDTO> woDTOList = Utils.mapWOListEntityToWODTO(workOrderList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setWorkOrderList(woDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving a room " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteWorkOrder(Long id) {
        Response response = new Response();

        try {
            woRepository.findById(id).orElseThrow(() -> new OurException("Work Order Not Found"));
            woRepository.deleteById(id);
            response.setStatusCode(200);
            response.setMessage("successful");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting work order " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateWorkOrder(Long id, MultipartFile photo, String title, String description, String status,
            String priority, LocalDateTime dueDate) {
        Response response = new Response();

        try {
            String imageUrl = null;
            if (photo != null && !photo.isEmpty()) {
                imageUrl = awsS3Service.saveImageToS3(photo);
            }
            WorkOrder wo = woRepository.findById(id).orElseThrow(() -> new OurException("Work Order Not Found"));
            if (title != null)
                wo.setTitle(title);
            if (description != null)
                wo.setDescription(description);
            if (status != null)
                wo.setStatus(status);
            if (priority != null)
                wo.setPriority(priority);
            if (dueDate != null)
                wo.setDueDate(dueDate);
            if (imageUrl != null)
                wo.setPhotoUrl(imageUrl);

            WorkOrder updatedwo = woRepository.save(wo);
            WorkOrderDTO woDTO = Utils.mapWorkOrderEntityToDTO(updatedwo);

            response.setStatusCode(200);
            response.setMessage("successful");
            response.setWorkOrder(woDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating the workOrder " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getWorkOrderById(String id) {
        Response response = new Response();
        try {
            WorkOrder wo = woRepository.findById(Long.valueOf(id))
                    .orElseThrow(() -> new OurException("Work Order Not Found"));
            WorkOrderDTO workOrderDTO = Utils.mapWorkOrderEntityToDTO(wo);

            response.setWorkOrder(workOrderDTO);
            response.setStatusCode(200);
            response.setMessage("successful");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting work order " + e.getMessage());
        }
        return response;
    }
    @Override
public Response getOpenWorkOrdersByPriority(String status, String priority) {
    Response response = new Response();

    try {
        if (priority == null || priority.isBlank()) {
            throw new OurException("Priority is required");
        }
        
    
        List<WorkOrder> workOrderList = woRepository.findByStatusAndPriority(status, priority);
        workOrderList.sort(Comparator.comparing(WorkOrder::getCreatedAt));
        
        if (workOrderList.isEmpty()) {
            response.setStatusCode(200);
            response.setMessage("No open work orders with priority: " + priority);
            response.setWorkOrderList(List.of());
        } else {
            List<WorkOrderDTO> woDTOList = Utils.mapWOListEntityToWODTO(workOrderList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setWorkOrderList(woDTOList);
        }

    } catch (OurException e) {
        response.setStatusCode(400);
        response.setMessage(e.getMessage());
    } catch (Exception e) {
        response.setStatusCode(500);
        response.setMessage("Error retrieving work orders by priority: " + e.getMessage());
    }
    return response;
}

@Override
public Response getWorkOrdersBeforeDueDate(LocalDateTime dueDate) {
    Response response = new Response();

    try {
        if (dueDate == null) {
            throw new OurException("Due date is required");
        }
        
        // Find all work orders with due date before the specified date
        List<WorkOrder> workOrderList = woRepository.findByDueDateBefore(dueDate);
        workOrderList.sort(Comparator.comparing(WorkOrder::getDueDate));
        
        if (workOrderList.isEmpty()) {
            response.setStatusCode(200);
            response.setMessage("No work orders due before: " + dueDate);
            response.setWorkOrderList(List.of());
        } else {
            List<WorkOrderDTO> woDTOList = Utils.mapWOListEntityToWODTO(workOrderList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setWorkOrderList(woDTOList);
        }

    } catch (OurException e) {
        response.setStatusCode(400);
        response.setMessage(e.getMessage());
    } catch (Exception e) {
        response.setStatusCode(500);
        response.setMessage("Error retrieving work orders by due date: " + e.getMessage());
    }
    return response;
}

}
