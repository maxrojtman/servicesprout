package com.Maxwell.ServiceSprout.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Maxwell.ServiceSprout.dto.Response;
import com.Maxwell.ServiceSprout.entity.User;
import com.Maxwell.ServiceSprout.service.interfac.IWorkOrderService;

@RestController
@RequestMapping("/workorders")
public class WorkOrderController {
    @Autowired
    private IWorkOrderService workOrderService; 

    @PostMapping("/create")
    public ResponseEntity<Response> createWorkOrder(
        @RequestParam(value = "photo", required= false) MultipartFile photo,
        @RequestParam(value = "title", required = true) String title,
        @RequestParam(value = "description", required = true) String description,
        @RequestParam(value = "status", required=false) String status,
        @RequestParam(value = "priority", required=false) String priority,
        @RequestParam(value = "dueDate", required=false) LocalDateTime dueDate) {
        Response response = new Response();
        
        if (title == null || title.isBlank()) {
            response.setStatusCode(400);
            response.setMessage("Title is required");   
        }
        else if (description == null || description.isBlank()) {
            response.setStatusCode(400);
            response.setMessage("Description is required");
        }
        else {
            response = workOrderService.createWorkOrder(photo, title, description, status, priority, dueDate);
        }
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/all")
    public ResponseEntity<Response> getAllWorkOrders() {
        Response response = workOrderService.getAllWorkOrders();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/get-by-id/{woId}")
    public ResponseEntity<Response> getWorkOrderById(@PathVariable("woId") String woId) {
        Response response = workOrderService.getWorkOrderById(woId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    
    @PutMapping("/update/{woId}")
    public ResponseEntity<Response> updateWorkOrder(
        @PathVariable("woId") Long woId,
        @RequestParam(value = "photo", required= false) MultipartFile photo,
        @RequestParam(value = "wotitle", required = true) String title,
        @RequestParam(value = "woDescription", required = true) String description,
        @RequestParam(value = "status", required=false) String status,
        @RequestParam(value = "priority", required=false) String priority,
        @RequestParam(value = "dueDate", required=false) LocalDateTime dueDate) {
        Response response = new Response();
        if (title == null || title.isBlank()) {
            response.setStatusCode(400);
            response.setMessage("Title is required");   
        }
        else if (description == null || description.isBlank()) {
            response.setStatusCode(400);
            response.setMessage("Description is required");
        }
        else {
            response = workOrderService.updateWorkOrder(woId, photo, title, description, status, priority, dueDate);
        }
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{woId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteWorkOrder(@PathVariable("woId") Long woId) {
        Response response = workOrderService.deleteWorkOrder(woId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/open-workorders-by-priority")
    public ResponseEntity<Response> getOpenWorkOrdersByPriority(
        @RequestParam(value = "status", required = true) String status,
        @RequestParam(value = "priority", required = true) String priority) {
        Response response = workOrderService.getOpenWorkOrdersByPriority(status, priority);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/workorders-before-due-date")
    public ResponseEntity<Response> getWorkOrdersBeforeDueDate(
        @RequestParam(value = "dueDate", required = true) LocalDateTime dueDate) {
        Response response = workOrderService.getWorkOrdersBeforeDueDate(dueDate);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


}
