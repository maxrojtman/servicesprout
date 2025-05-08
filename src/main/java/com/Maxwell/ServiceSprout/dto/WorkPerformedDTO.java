package com.Maxwell.ServiceSprout.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class WorkPerformedDTO {
    private Long id;
    private String note;
    private LocalDateTime timestamp;

    private Long technicianId; 
    private Long workOrderId;
}